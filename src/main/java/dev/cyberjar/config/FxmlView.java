package dev.cyberjar.config;

public enum FxmlView {

    HOME {

        @Override
        public String getFxmlPath() {
            return "/fxml/home.fxml";
        }
    },

    START {

        @Override
        public String getFxmlPath() {
            return "/fxml/starter.fxml";
        }
    },

    RAFFLE {

        @Override
        public String getFxmlPath() {
            return "/fxml/randomizer.fxml";
        }
    };

    public abstract String getFxmlPath();

}
