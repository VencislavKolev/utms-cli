//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//public class Bin {
//    private void option4() throws Exception {
//        final File yamlFile = new File(YAML_FILEPATH);
//        final File outputDirectory = yamlFile.getParentFile().getParentFile();
//
//        // Step 1: Read in the YAML file, into class specifications
//        YamlClassSpecificationReader yamlReader = new YamlClassSpecificationReader();
//        List<ClassSpecification> classSpecifications = yamlReader.read(yamlFile);
//
//        // Step 2: Generate Java source files from the class specifications
//        JavaDataClassGenerator javaDataClassGenerator = new JavaDataClassGenerator();
//        javaDataClassGenerator.generateJavaSourceFiles(classSpecifications, outputDirectory);
//
//        System.out.println("Successfully generated files to: " + outputDirectory.getAbsolutePath());
//    }
//
//
//private void castYamlSnakeYaml() {
//        Yaml yaml = new Yaml(new Constructor(YamlDto.class));
//        InputStream inputStream = getInputStream();
//
//        YamlDto data = yaml.load(inputStream);
//        System.out.println(data);
//        }

// for (var suiteTest : suiteTests) {
//            Map<String, TestDetail> fullDetailMap = suiteTest.getTests()
//                    .entrySet()
//                    .stream()
//  .forEach(kvp -> System.out.printf("Name: %s - %s%n", kvp.getKey(), kvp.getValue()));
//                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//}

//        try {
//            this.yamlUtil.parseYamlWithJackson(filePath);
//        } catch (IOException e) {
//           // e.printStackTrace();
//            this.printJsonString(new ErrorDto("Configuration file is not valid."));
//        }



// this.readYamlAsMap();

//        try {
//            this.parseYamlWithJackson();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//---------NEW-------------
//        YamlDto yamlDto = null;
//        try {
//            yamlDto = this.yamlParser.parseYamlWithJackson(TESTING2);
//            this.jsonReportService.generateJson(yamlDto);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try {
//            this.jsonReportService.generateJson(NONEXISTING_YAML,"2");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        File file = new File(filePath);
//        if (!file.isFile()) {
//            return new ErrorDto("Configuration file not found.");
//        }
////        if (!this.fileUtil.checkIfExists(filePath)) {
////            return new ErrorDto("Configuration file not found.");
////        }
//
//

//-------------------
//ReportDto result = this.jsonService.processInput("--config", "testing.yaml", "--run-id", "1");
//ReportDto result = this.jsonService.processInput("--config", "skipping.yaml");

//--------------BONUS ARGUMENTS---------
//ReportDto result = this.jsonService.processInput("--config", "testing.yaml", "--suite-name", "UITests");

//ReportDto result = this.jsonService.processInput("--config", "testing.yaml", "--suite-name", "UITests", "--test-name", "Test4");
//ReportDto result = this.jsonService.processInput("--config", "testing.yaml", "--suite-name", "UITests", "--test-name", "Test1");
//ReportDto result = this.jsonService.processInput("--config", "testing.yaml", "--test-name", "Test4");

//-------------------SHORT COMMANDS-------------
//ReportDto result = this.jsonService.processInput("-c", "testing.yaml", "-sn", "UITests", "-tn", "Test4");
//ReportDto result = this.jsonService.processInput("-c", "testing.yaml", "-sn", "UITests", "-tn", "Test8");
//ReportDto result = this.jsonService.processInput("-c", "testing.yaml", "-sn", "UITests");

//----------------------------------RUN_ID-REMOVED------------------------------------
//        String inputRunId = null;
//        if (cmdMap.containsKey(RUN_CMD)) {
//            inputRunId = cmdMap.remove(RUN_CMD);
//            boolean isNumber = this.isValidNumber(inputRunId);
//            if (!isNumber) {
//                return new ReportDto(INVALID_RUN_ID);
//            }
//        }

//private boolean isValidNumber(String str) {
//        // null pointer
//        if (str == null) {
//        return false;
//        }
//        int len = str.length();
//        // empty string
//        if (len == 0) {
//        return false;
//        }
//        // one digit, cannot begin with 0
//        if (len == 1) {
//        char c = str.charAt(0);
//        if ((c < '1') || (c > '9')) {
//        return false;
//        }
//        }
//
//        for (int i = 0; i < len; i++) {
//        char c = str.charAt(i);
//        // check positive, negative sign
//        if (i == 0) {
//        // only positive sign accepted
//        if (c == '+' || Character.isDigit(c)) {
//        continue;
//        }
//        return false;
//        }
//        // check each character matches [0-9]
//        if ((c < '0') || (c > '9')) {
//        return false;
//        }
//        }
//        return true;
//        }