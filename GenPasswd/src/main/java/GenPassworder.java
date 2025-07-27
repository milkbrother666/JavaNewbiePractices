import java.security.SecureRandom;
import java.util.Objects;

/**
 * TODO: 支持指定密码长度范围
 * TODO: 详细划分大小写字母的个数
 * TODO: 支持指定一次性生成密码的个数
 * TODO: 优化程序逻辑
 * TODO: 支持指定固定个数的大、小写字母、数字、符号组合，当其与总长度冲突时，优先前者
 */

public class GenPassworder {
    private Integer total_length = 12;
    private Integer max_number_count;
    private Integer min_number_count;
    private Integer max_alpha_count;
    private Integer min_alpha_count;
    private Integer max_symbols_count;
    private Integer min_symbols_count;
    private String symbols_string;
    private String alpha_string;

    private void recalculate(){
//        total_length = 12;
        max_number_count = (int) Math.round(total_length * 0.4);
        min_number_count = max_number_count;
        max_alpha_count = max_number_count;
        min_alpha_count = max_number_count;
        max_symbols_count = (int) Math.round(total_length * 0.2);
        min_symbols_count = 1;
        symbols_string = "!@$^./|";
        alpha_string = DString.ASCII_LETTERS;
    }

    public GenPassworder() {
        recalculate();
    }

    public GenPassworder(Integer total_length, Integer max_number_count, Integer min_number_count, Integer max_alpha_count, Integer min_alpha_count, Integer max_symbols_count, Integer min_symbols_count, String symbols_string) {
        total_length = 12;
        max_number_count = (int) Math.round(total_length * 0.4);
        min_number_count = max_number_count;
        max_alpha_count = max_number_count;
        min_alpha_count = max_number_count;
        max_symbols_count = (int) Math.round(total_length * 0.2);
        min_symbols_count = 1;
        symbols_string = "!@$^./|";
        alpha_string = DString.ASCII_LETTERS;
    }

    private int get_number_counts() {
        // 获取生成的密码中的数字的个数
        int number_count;
        if (!Objects.equals(this.max_number_count, this.min_number_count)) {
            SecureRandom srd = new SecureRandom();
            number_count = srd.nextInt(this.min_number_count, this.max_number_count + 1);
        } else {
            number_count = this.min_number_count;
        }
        return number_count;
    }

    private int get_alpha_counts() {
        // 获取生成的密码中的字母的个数
        int alpha_count;
        if (!Objects.equals(this.max_alpha_count, this.min_alpha_count)) {
            SecureRandom srd = new SecureRandom();
            alpha_count = srd.nextInt(this.min_alpha_count, this.max_alpha_count + 1);
        } else {
            alpha_count = this.min_alpha_count;
        }
        return alpha_count;
    }

    private int get_symbols_counts() {
        // 获取生成的密码中的符号的个数
        int symbols_count;
        if (!Objects.equals(this.max_symbols_count, this.min_symbols_count)) {
            SecureRandom srd = new SecureRandom();
            symbols_count = srd.nextInt(this.min_symbols_count, this.max_symbols_count + 1);
        } else {
            symbols_count = this.min_symbols_count;
        }
        return symbols_count;
    }

    public String start() {
        int alpha_count = 0;
        int number_count = 0;
        int symbols_count = 0;
        int retry = 0; // 尝试次数
        while (alpha_count + number_count + symbols_count != total_length) {
            if (retry <= 3) {
                alpha_count = get_alpha_counts();
                number_count = get_number_counts();
                symbols_count = get_symbols_counts();
                retry++;
            } else {
                symbols_count = total_length - alpha_count - number_count;
                break;
            }
        }
        String alpha_got = "";
        String number_got = "";
        String symbols_got = "";

        char[] alpha_string_to_chars = alpha_string.toCharArray();
        char[] symbols_string_to_chars = symbols_string.toCharArray();
        SecureRandom sr = new SecureRandom();

        for (int i = 0; i < this.total_length + 1; i++) {
            if (alpha_got.length() < alpha_count) {
                alpha_got += alpha_string_to_chars[sr.nextInt(alpha_string_to_chars.length)];
            } else if (number_got.length() < number_count) {
                number_got += sr.nextInt(0, 10);
            } else if (symbols_got.length() < symbols_count) {
                symbols_got += symbols_string_to_chars[sr.nextInt(symbols_string_to_chars.length)];
            }
        }

        StringBuilder sb = new StringBuilder(alpha_got);
        sb.append(number_got);
        sb.append(symbols_got);


        String new_pass = "";
        for (int i = 0; i < total_length; i++) {
            char[] sb_to_chars = sb.toString().toCharArray();
            int sr_c_index = sr.nextInt(sb_to_chars.length);
            char sr_c = sb_to_chars[sr_c_index];
            new_pass += sr_c;
            sb.deleteCharAt(sr_c_index);
        }

        return new_pass;
    }


    public Integer getMax_number_count() {
        return max_number_count;
    }

    public void setMax_number_count(Integer max_number_count) {
        this.max_number_count = max_number_count;
        recalculate();
    }

    public Integer getMin_number_count() {
        return min_number_count;
    }

    public void setMin_number_count(Integer min_number_count) {
        this.min_number_count = min_number_count;
        recalculate();
    }

    public Integer getMax_alpha_count() {
        return max_alpha_count;
    }

    public void setMax_alpha_count(Integer max_alpha_count) {
        this.max_alpha_count = max_alpha_count;
        recalculate();
    }

    public Integer getMin_alpha_count() {
        return min_alpha_count;
    }

    public void setMin_alpha_count(Integer min_alpha_count) {
        this.min_alpha_count = min_alpha_count;
        recalculate();
    }

    public Integer getMax_symbols_count() {
        return max_symbols_count;
    }

    public void setMax_symbols_count(Integer max_symbols_count) {
        this.max_symbols_count = max_symbols_count;
        recalculate();
    }

    public Integer getMin_symbols_count() {
        return min_symbols_count;
    }

    public void setMin_symbols_count(Integer min_symbols_count) {
        this.min_symbols_count = min_symbols_count;
        recalculate();
    }

    public String getSymbols_string() {
        return symbols_string;
    }

    public void setSymbols_string(String symbols_string) {
        this.symbols_string = symbols_string;
        recalculate();
    }

    public String getAlpha_string() {
        return alpha_string;
    }

    public void setAlpha_string(String alpha_string) {
        this.alpha_string = alpha_string;
        recalculate();
    }

    public Integer getTotal_length() {
        return total_length;
    }

    public void setTotal_length(Integer total_length) {
        this.total_length = total_length;
        recalculate();
    }
}
