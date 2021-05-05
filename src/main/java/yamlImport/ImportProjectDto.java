package yamlImport;

public class ImportProjectDto {
        private String name;
        private String description;

    public ImportProjectDto() {
    }

    public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String value) {
            this.description = value;
        }
    }
