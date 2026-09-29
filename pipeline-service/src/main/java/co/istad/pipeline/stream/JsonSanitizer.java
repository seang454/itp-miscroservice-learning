//package co.istad.pipeline.stream;
//
//public class JsonSanitizer {
//    private JsonSanitizer() {}
//
//    public static String clean(String input) {
//        if (input == null) return null;
//
//        return input
//                // Remove ALL illegal ASCII control chars except \r \n \t
//                .replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F]", "");
//    }
//}
