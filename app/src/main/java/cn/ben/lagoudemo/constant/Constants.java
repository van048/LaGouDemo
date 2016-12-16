package cn.ben.lagoudemo.constant;

public class Constants {

    public class Login {
        public static final float KEYBOARD_ANIM_EDIT_TEXT_FINAL_Y = -120;
        public static final long KEYBOARD_ANIM_DURATION = 300;
        public static final int PASSWORD_MIN_LEN = 6;
        public static final int PASSWORD_MAX_LEN = 16;
    }

    public enum ErrorMessage {
        USER_NOT_FOUND("账号和密码不匹配"),
        PW_LENGTH_NOT_RIGHT("请输入6～16位密码");
        private String message;

        private ErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
