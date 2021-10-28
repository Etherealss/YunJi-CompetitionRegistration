package pers.etherealss.utils.simple;

import sun.misc.BASE64Encoder;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Random;

/**
 * @author wtk
 * @description 图片工具类
 * @date 2021-10-05
 */
public class ImgUtil {
    /**
     * 图片类型
     */
    public static final String IMAGE_TYPE_GIF = "gif";
    public static final String IMAGE_TYPE_JPG = "jpg";
    public static final String IMAGE_TYPE_JPEG = "jpeg";
    public static final String IMAGE_TYPE_BMP = "bmp";
    public static final String IMAGE_TYPE_PNG = "png";
    public static final String IMAGE_TYPE_PSD = "psd";

    /**
     * 将数据以Base64转码返回
     * @param data
     * @return Base64转码后的数据流
     */
    public static String getImg4Base64(byte[] data) {
        //Base64转码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * RGB颜色范围上限
     */
    private static final int RGB_COLOR_BOUND = 256;

    /**
     * 生成随机颜色
     *
     * @return 随机颜色
     * @since 3.1.2
     */
    public static Color randomColor() {
        return randomColor(null);
    }

    /**
     * 生成随机颜色
     *
     * @param random 随机对象 {@link Random}
     * @return 随机颜色
     * @since 3.1.2
     */
    public static Color randomColor(Random random) {
        if (null == random) {
            random = RandomUtil.random();
        }
        return new Color(random.nextInt(RGB_COLOR_BOUND), random.nextInt(RGB_COLOR_BOUND), random.nextInt(RGB_COLOR_BOUND));
    }

    /**
     * 写出图像为PNG格式
     * @param image
     * @param out 写出到的目标流
     * @throws IOException
     */
    public static void writePng(Image image, OutputStream out) throws IOException {
        write(image, IMAGE_TYPE_PNG, out);
    }

    public static void write(Image image, String imageType, OutputStream out) throws IOException {
        // 将 OutputStream 转为 ImageOutputStream
        write(image, imageType, getImageOutputStream(out));
    }

    public static boolean write(Image image, String imageType,
                                ImageOutputStream destImageStream) throws IOException {
        // 图片质量为1
        return write(image, imageType, destImageStream, 1);
    }

    /**
     *
     * @param image
     * @param imageType
     * @param destImageStream
     * @param quality 质量，数字为0~1（不包括0和1）表示质量压缩比，除此数字外设置表示不压缩
     * @return
     * @throws IOException
     */
    public static boolean write(Image image, String imageType,
                                ImageOutputStream destImageStream, float quality) throws IOException {
        if (StringUtil.isBlank(imageType)) {
            imageType = IMAGE_TYPE_JPG;
        }
        // 获取 ImageWriter ，通过它绘制
        final ImageWriter writer = getWriter(image, imageType);
        return write(toBufferedImage(image, imageType), writer, destImageStream, quality);
    }

    /**
     * 通过{@link ImageWriter}写出图片到输出流
     *
     * @param image   图片
     * @param writer  {@link ImageWriter}
     * @param output  输出的Image流{@link ImageOutputStream}
     * @param quality 质量，数字为0~1（不包括0和1）表示质量压缩比，除此数字外设置表示不压缩
     * @return 是否成功写出
     */
    public static boolean write(Image image, ImageWriter writer,
                                ImageOutputStream output, float quality) throws IOException {
        if (writer == null) {
            return false;
        }

        writer.setOutput(output);
        final RenderedImage renderedImage = toRenderedImage(image);
        // 设置质量
        ImageWriteParam imgWriteParams = null;
        if (quality > 0 && quality < 1) {
            imgWriteParams = writer.getDefaultWriteParam();
            if (imgWriteParams.canWriteCompressed()) {
                imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                imgWriteParams.setCompressionQuality(quality);
                final ColorModel colorModel = renderedImage.getColorModel();
                imgWriteParams.setDestinationType(
                        new ImageTypeSpecifier(
                                colorModel, colorModel.createCompatibleSampleModel(16, 16)
                        )
                );
            }
        }

        try {
            if (null != imgWriteParams) {
                writer.write(null, new IIOImage(renderedImage, null, null), imgWriteParams);
            } else {
                writer.write(renderedImage);
            }
            output.flush();
        } finally {
            writer.dispose();
        }
        return true;
    }

    /**
     * 获取{@link ImageOutputStream}
     *
     * @param out {@link OutputStream}
     * @return {@link ImageOutputStream}
     * @throws IOException IO异常
     */
    public static ImageOutputStream getImageOutputStream(OutputStream out) throws IOException {
        ImageOutputStream result = ImageIO.createImageOutputStream(out);
        if (null == result) {
            throw new IllegalArgumentException("图片类型不支持！");
        }
        return result;
    }

    /**
     * 根据给定的Image对象和格式获取对应的{@link ImageWriter}，如果未找到合适的Writer，返回null
     *
     * @param img        {@link Image}
     * @param formatName 图片格式，例如"jpg"、"png"
     * @return {@link ImageWriter}
     */
    public static ImageWriter getWriter(Image img, String formatName) {
        final ImageTypeSpecifier type = ImageTypeSpecifier.createFromRenderedImage(toRenderedImage(img));
        final Iterator<ImageWriter> iter = ImageIO.getImageWriters(type, formatName);
        return iter.hasNext() ? iter.next() : null;
    }

    /**
     * {@link Image} 转 {@link RenderedImage}<br>
     * 首先尝试强转，否则新建一个{@link BufferedImage}后重新绘制
     */
    public static RenderedImage toRenderedImage(Image img) {
        if (img instanceof RenderedImage) {
            return (RenderedImage) img;
        }

        return copyImage(img, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * 将已有Image复制新的一份出来
     *
     * @param img       {@link Image}
     * @param imageType 目标图片类型，{@link BufferedImage}中的常量，例如黑白等
     * @return {@link BufferedImage}
     * @see BufferedImage#TYPE_INT_RGB
     * @see BufferedImage#TYPE_INT_ARGB
     * @see BufferedImage#TYPE_INT_ARGB_PRE
     * @see BufferedImage#TYPE_INT_BGR
     * @see BufferedImage#TYPE_3BYTE_BGR
     * @see BufferedImage#TYPE_4BYTE_ABGR
     * @see BufferedImage#TYPE_4BYTE_ABGR_PRE
     * @see BufferedImage#TYPE_BYTE_GRAY
     * @see BufferedImage#TYPE_USHORT_GRAY
     * @see BufferedImage#TYPE_BYTE_BINARY
     * @see BufferedImage#TYPE_BYTE_INDEXED
     * @see BufferedImage#TYPE_USHORT_565_RGB
     * @see BufferedImage#TYPE_USHORT_555_RGB
     */
    public static BufferedImage copyImage(Image img, int imageType) {
        return copyImage(img, imageType, null);
    }

    public static BufferedImage copyImage(Image img, int imageType, Color backgroundColor) {
        final BufferedImage bimage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), imageType
        );
        final Graphics2D bGr = GraphicsUtil.createGraphics(bimage, backgroundColor);
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }

    /**
     * {@link Image} 转 {@link BufferedImage}<br>
     * 如果源图片的RGB模式与目标模式一致，则直接转换，否则重新绘制
     *
     * @param imageType 目标图片类型
     */
    public static BufferedImage toBufferedImage(Image image, String imageType) {
        BufferedImage bufferedImage;
        if (!imageType.equalsIgnoreCase(IMAGE_TYPE_PNG)) {
            // 当目标为非PNG类图片时，源图片统一转换为RGB格式
            if (image instanceof BufferedImage) {
                bufferedImage = (BufferedImage) image;
                if (BufferedImage.TYPE_INT_RGB != bufferedImage.getType()) {
                    // 重新绘制
                    bufferedImage = copyImage(image, BufferedImage.TYPE_INT_RGB);
                }
            } else {
                // 重新绘制
                bufferedImage = copyImage(image, BufferedImage.TYPE_INT_RGB);
            }
        } else {
            // 是PNG，重新绘制
            bufferedImage = toBufferedImage(image);
        }
        return bufferedImage;
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        return copyImage(img, BufferedImage.TYPE_INT_RGB);
    }
}
