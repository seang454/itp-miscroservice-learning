# ITP Microservices Production Setup Guide: ZTNA (Tailscale HA) + mirrord + Kubernetes

This comprehensive guide is specifically tailored to your **ITP Banking Microservices Ecosystem** (`pipeline-service`, `account-query-service`, `account-service`, `customer-service`, `gateway-server`, `itp-identity-service`).

It explains how to connect your local Windows developer environment (IntelliJ IDEA) to your remote 9-node Kubernetes cluster (`benzcluster`) using **Zero Trust Network Access (ZTNA) with High Availability Tailscale** and **Open-Source mirrord (Free, Zero Cluster Operator needed)**.

---

## 1. Complete Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       DEVELOPER LAPTOP (WINDOWS)                            │
│                                                                             │
│   IntelliJ IDEA (Local Code & Live Breakpoints)                             │
│   ├── pipeline-service (Port 20262)                                         │
│   ├── account-query-service (Port 20263 - WebFlux)                          │
│   ├── account-service (Port 20261)                                          │
│   └── customer-service (Port 20260)                                         │
│                                                                             │
│   mirrord Hook (Intercepts DNS & Outbound TCP Calls)                        │
│   Tailscale Client (Connected via Google Workspace/Okta + MFA)              │
└──────────────────────────────────────┬──────────────────────────────────────┘
                                       │
                                       │ 🔐 WireGuard Encrypted Mesh Tunnel
                                       │ (Public Port 6443 CLOSED to internet)
                                       ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                      KUBERNETES CLUSTER (9-NODE BENZCLUSTER)                │
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │ Tailscale HA Subnet Routers (Active/Standby Failover < 2s)            │  │
│  │ • master01: 10.0.0.1 (Tailscale: 100.73.89.70) - ACTIVE               │  │
│  │ • master02: 10.0.0.2 (Tailscale: 100.127.20.122) - STANDBY BACKUP     │  │
│  │ Subnet Advertised & Approved: 10.0.0.0/16                             │  │
│  └───────────────────────────────────┬───────────────────────────────────┘  │
│                                      ▼                                      │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │ Kubernetes API Server (Private IP: 10.0.0.1:6443)                     │  │
│  └───────────────────────────────────┬───────────────────────────────────┘  │
│                                      │                                      │
│      ════════════════════════════════╪═════════════════════════════════     │
│      Calico Virtual Pod Mesh (10.233.0.0/16 across 9 physical machines)      │
│      ════════════════════════════════╪═════════════════════════════════     │
│                                      │                                      │
│      ┌───────────────────────────────┴───────────────────────────────┐      │
│      │ mirrord-agent (Ephemeral Pod created automatically on-demand) │      │
│      └───────┬───────────────────────────────┬───────────────────────┘      │
│              │                               │                              │
│              ▼                               ▼                              │
│   ┌───────────────────────┐       ┌───────────────────────┐                 │
│   │ Grafana Alloy (DS)    │       │ Kafka Cluster (Brokers│                 │
│   │ alloy.monitoring:4318 │       │ kafka-1/2/3:9092)     │                 │
│   └──────────┬────────────┘       └──────────┬────────────┘                 │
│              ▼                               ▼                              │
│   Tempo / Loki / Pyroscope        Debezium / CDC Pipeline                   │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 2. Phase 1: Tailscale High Availability (HA) Setup

> [!NOTE]
> Both `master01` (`100.73.89.70`) and `master02` (`100.127.20.122`) are already installed and connected in your Tailscale Admin Console.

### 2.1 Approve Subnet Routes for High Availability Failover
1. Open [login.tailscale.com/admin/machines](https://login.tailscale.com/admin/machines).
2. For **`master01`**: Click `...` $\rightarrow$ **Edit route settings...** $\rightarrow$ Check **`10.0.0.0/16`** $\rightarrow$ **Save**.
3. For **`master02`**: Click `...` $\rightarrow$ **Edit route settings...** $\rightarrow$ Check **`10.0.0.0/16`** $\rightarrow$ **Save**.
4. Both nodes are now bound as an **Active/Standby High Availability pair**. If `master01` reboots, `master02` takes over in $< 2$ seconds with zero disconnection!

### 2.2 Join Your Windows Laptop
1. In the Windows taskbar, right-click the Tailscale icon $\rightarrow$ **Log in...**.
2. Sign in with the same email (`pengseangsim210@gmail.com`).
3. Verify connection in PowerShell:
   ```powershell
   ping 100.73.89.70
   ```

---

## 3. Phase 2: Secure Kubeconfig & Close Public Port 6443

### 3.1 Restore and Update `C:\Users\M\.kube\config`
1. On `master01`, copy the complete configuration:
   ```bash
   sudo cat /etc/kubernetes/admin.conf
   ```
2. Paste the entire content into `C:\Users\M\.kube\config` on your Windows laptop.
3. Change **only** the `server:` line to the private IP (reachable through Tailscale):
   ```yaml
   server: https://10.0.0.1:6443
   ```
4. Verify from PowerShell:
   ```powershell
   kubectl get nodes -o wide
   ```
   *(All 9 nodes will respond cleanly through your encrypted WireGuard tunnel!)*

### 3.2 Close Public Port 6443 on the Server Firewall
On your cloud security group or router firewall, remove/disable the rule allowing port `6443` from `0.0.0.0/0`.
Your Kubernetes control plane is now completely **dark (invisible)** to public internet scanners!

---

## 4. Phase 3: Open-Source mirrord Setup (Zero Cluster Install)

> [!IMPORTANT]
> **You do NOT need to install any Helm chart or Operator in Kubernetes!**
> The **mirrord Operator** is a paid commercial product for large enterprises.
> The **Open-Source mirrord CLI & IntelliJ Plugin** is **100% Free (MIT Licensed)** and works **operator-less**:
> * When you click Debug in IntelliJ, mirrord automatically spins up an ephemeral `mirrord-agent` pod.
> * When you stop debugging, mirrord automatically destroys the pod.

### 4.1 Install mirrord on Windows
* **CLI (PowerShell)**:
  ```powershell
  winget install MetalBear.mirrord
  ```
* **IntelliJ Plugin**:
  * Open IntelliJ $\rightarrow$ `Settings` (`Ctrl + Alt + S`) $\rightarrow$ `Plugins` $\rightarrow$ Marketplace $\rightarrow$ Search **`mirrord`** $\rightarrow$ Click **Install** $\rightarrow$ Restart IntelliJ.

### 4.2 Project Configuration: `.mirrord/mirrord.json`
Your project configuration is located at `itp-microservice/.mirrord/mirrord.json`:

```json
{
  "$schema": "https://raw.githubusercontent.com/metalbear-co/mirrord/main/mirrord-schema.json",
  "target": {
    "path": null,
    "namespace": "monitoring"
  },
  "feature": {
    "network": {
      "incoming": "mirror",
      "outgoing": true,
      "dns": true
    },
    "env": true,
    "fs": "local"
  }
}
```

---

## 5. Phase 4: Configure Your ITP Microservices for In-Cluster DNS

Because mirrord hooks DNS resolution, your local Spring Boot applications can resolve in-cluster `.svc.cluster.local` names directly!

### 5.1 OpenTelemetry & Tracing (Grafana Alloy)
Add this to `src/main/resources/application.yaml` across your services (`pipeline-service`, `account-query-service`, `account-service`):

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus,metrics
  tracing:
    sampling:
      probability: 1.0
  otlp:
    tracing:
      # Direct internal K8s DNS to Alloy in the monitoring namespace
      endpoint: http://alloy.monitoring.svc.cluster.local:4318/v1/traces
```

### 5.2 Apache Kafka (Messaging & CDC)
For `pipeline-service` and `account-query-service`:

```yaml
spring:
  kafka:
    bootstrap-servers: kafka-1-itp-0.kafka-headless.kafka.svc.cluster.local:9092,kafka-2-itp-0.kafka-headless.kafka.svc.cluster.local:9092,kafka-3-itp-0.kafka-headless.kafka.svc.cluster.local:9092
    properties:
      schema.registry.url: http://schema-registry.kafka.svc.cluster.local:8081
```

---

## 6. Phase 5: Run & Debug Your Services

### Running via IntelliJ IDEA (Recommended)
1. Ensure Tailscale is connected (green icon in taskbar).
2. Open any of your microservices:
   * **`PipelineServiceApplication.java`** (CDC / Avro Processing, Port `20262`)
   * **`AccountQueryServiceApplication.java`** (Reactive WebFlux CQRS Read, Port `20263`)
   * **`AccountServiceApplication.java`** (CQRS Write, Port `20261`)
3. Click the **mirrord icon** in the top IntelliJ toolbar (turn it **Active / Green**).
4. Click **Debug** (`Shift + F9`).
5. Set breakpoints anywhere in your service code!

### Running via PowerShell Terminal
```powershell
# Run pipeline-service
cd d:\CSTADPreUniversityTraining\ITP\researchingitp\itp-microservice-learn\itp-microservice\pipeline-service
mirrord exec -- ./gradlew bootRun

# Or run account-query-service
cd d:\CSTADPreUniversityTraining\ITP\researchingitp\itp-microservice-learn\itp-microservice\account-query-service
mirrord exec -- ./gradlew bootRun
```

---

## 7. Verification & Observability in Grafana

1. Open Swagger UI in your browser:
   * `pipeline-service`: `http://localhost:20262/swagger-ui.html`
   * `account-query-service`: `http://localhost:20263/swagger-ui.html`
2. Execute an API request (or trigger an account event).
3. IntelliJ pauses on your breakpoint — step through your local code!
4. Open **Grafana UI** in your cluster $\rightarrow$ **Explore** $\rightarrow$ **Tempo**:
   * Filter by Service: `pipeline-service` or `account-query-service`.
   * **You will see the trace delivered from your local Windows laptop directly into Grafana Alloy!**
