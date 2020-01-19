package com.golang.management.bean;

public class EideBean {
        /**
         * into : false
         * disLevel : GOLD_MEMBER
         */

        private boolean into;
        private String disLevel;

        public boolean isInto() {
            return into;
        }

        public void setInto(boolean into) {
            this.into = into;
        }

        public String getDisLevel() {
            return disLevel;
        }

        public void setDisLevel(String disLevel) {
            this.disLevel = disLevel;
        }
}
