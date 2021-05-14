//package tmp;
//
//import com.amihaiemil.eoyaml.YamlMapping;
//import de.bertilmuth.javadataclass.generate.JavaDataClassGenerator;
//import de.bertilmuth.javadataclass.model.ClassSpecification;
//import de.bertilmuth.javadataclass.read.YamlClassSpecificationReader;
//
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
//    private void option3() throws IOException {
//        YamlMapping team = com.amihaiemil.eoyaml.Yaml.createYamlInput(
//                new File(YAML_FILEPATH))
//                .readYamlMapping();
//        System.out.println();
//    }
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
//private static final String YAML_FILEPATH = "./testing.yaml";
//private static final String TESTING2 = "/testing2.yaml";
//
//private static final String INVALID_YAML = "./not-valid-testing.yaml";
//private static final String NONEXISTING_YAML = "varvarvar";
