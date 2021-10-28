package pers.etherealss.utils.captcha.po;

import lombok.Getter;
import lombok.Setter;
import pers.etherealss.utils.captcha.generator.CaptchaCodeGenerator;
import pers.etherealss.utils.captcha.generator.RandomCaptchaCodeGenerator;
import pers.etherealss.utils.simple.FileUtil;
import pers.etherealss.utils.simple.ImgUtil;
import pers.etherealss.utils.simple.IoUtil;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wtk
 * @description 抽象验证码类
 * @date 2021-10-05
 */
@Setter
@Getter
public abstract class AbstractCaptcha implements Captcha {

    /** 图片的宽度 */
    protected int width;
    /** 图片的高度 */
    protected int height;
    /** 验证码干扰元素个数 */
    protected int interfereCount;
    /** 字体 */
    protected Font font;
    /** 背景色 */
    protected Color background;
    /** 验证码，用于输出与验证码图片，也会用于检验 */
    protected String code;
    /** 验证码图片 */
    protected byte[] imageBytes;
    /** 验证码生成器 */
    protected CaptchaCodeGenerator generator;
    /** 文字透明度 */
    protected AlphaComposite textAlpha;
    /** 验证码过期处理器，默认5分钟超时 */
    protected CaptchaExpirationChecker expirationHandler
            = new DefaultCaptchaExpirationChecker(5);

    @Override
    public void createCode() throws IOException {
        // 生成code
        generateCode();

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 创建图片
        Image image = createImage(this.code);
        // 将图片写入流中
        ImgUtil.writePng(image, out);
        this.imageBytes = out.toByteArray();
    }

    /**
     * 通过生成器生成code
     */
    protected void generateCode() {
        this.code = generator.generate();
    }

    /**
     * 根据生成的code创建验证码图片，由子类实现不同的逻辑
     * @param code 验证码文本
     * @return Image
     */
    protected abstract Image createImage(String code);

    @Override
    public String getCode() {
        if (null == this.code) {
            try {
                createCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.code;
    }

    @Override
    public boolean verify(String userInputCode) {
        // 验证
        return this.generator.verify(getCode(), userInputCode);
    }

    /**
     * 获取图形验证码图片bytes
     * 如果bytes还未生成，则会调用生成代码
     * @return 图形验证码图片bytes
     * @since 4.5.17
     */
    public byte[] getImageBytes() {
        if (null == this.imageBytes) {
            try {
                createCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.imageBytes;
    }

    /**
     * 验证码写出到文件，如果验证码图片还未生成，则会自动生成
     * @param path 文件路径
     * @throws IOException IO异常
     */
    public void write(String path) throws IOException {
        this.write(FileUtil.touch(path));
    }

    /**
     * 验证码写出到文件，如果验证码图片还未生成，则会自动生成
     * @param file 文件
     * @throws IOException IO异常
     */
    public void write(File file) throws IOException {
        try (OutputStream out = FileUtil.getOutputStream(file)) {
            this.write(out);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void write(OutputStream out) throws IOException {
        IoUtil.write(out, false, getImageBytes());
    }

    /**
     * 使用随机验证码生成器生成验证码
     * @param width 图片宽
     * @param height 图片高
     * @param codeCount 字符个数
     * @param interfereCount 验证码干扰元素个数
     */
    public AbstractCaptcha(int width, int height, int codeCount, int interfereCount) {
        this(width, height, new RandomCaptchaCodeGenerator(codeCount), interfereCount);
    }

    /**
     * @param width 图片宽
     * @param height 图片高
     * @param generator 验证码生成器
     * @param interfereCount 验证码干扰元素个数
     */
    public AbstractCaptcha(int width, int height, CaptchaCodeGenerator generator, int interfereCount) {
        this.width = width;
        this.height = height;
        this.generator = generator;
        this.interfereCount = interfereCount;
        // 字体高度设为验证码高度-2，留边距
        this.font = new Font(Font.SANS_SERIF, Font.PLAIN, (int) (this.height * 0.75));
    }
}
