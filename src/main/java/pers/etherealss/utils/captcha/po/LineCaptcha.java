package pers.etherealss.utils.captcha.po;

import pers.etherealss.utils.simple.GraphicsUtil;
import pers.etherealss.utils.simple.ImgUtil;
import pers.etherealss.utils.simple.RandomUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
public class LineCaptcha extends AbstractCaptcha {
    /**
     * 默认4位验证码，50条干扰线
     * @param width 图片宽
     * @param height 图片高
     */
    public LineCaptcha(int width, int height) {
        this(width, height, 4, 50);
    }

    /**
     * @param width 图片宽
     * @param height 图片高
     * @param codeCount 字符个数
     * @param lineCount 干扰线条数
     */
    public LineCaptcha(int width, int height, int codeCount, int lineCount) {
        super(width, height, codeCount, lineCount);
    }

    @Override
    protected Image createImage(String code) {
        // 图像buffer
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 默认白色
        Color color = (this.background == null) ? Color.WHITE : this.background;
        final Graphics2D g = GraphicsUtil.createGraphics(image, color);

        // 干扰线
        drawInterfere(g);

        // 字符串
        drawString(g, code);

        return image;
    }

    /**
     * 绘制干扰线
     */
    private void drawInterfere(Graphics2D g) {
        final ThreadLocalRandom random = RandomUtil.random();
        // 干扰线
        for (int i = 0; i < this.interfereCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            g.setColor(ImgUtil.randomColor(random));
            g.drawLine(xs, ys, xe, ye);
        }
    }

    /**
     * 绘制字符串
     *
     * @param g {@link Graphics}画笔
     * @param code 验证码
     */
    private void drawString(Graphics2D g, String code) {
        // 指定透明度
        if (null != this.textAlpha) {
            g.setComposite(this.textAlpha);
        }
        GraphicsUtil.drawStringColourful(g, code, this.font, this.width, this.height);
    }

}
