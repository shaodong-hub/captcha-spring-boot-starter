package com.github.verification.captcha.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * <p>
 * 创建时间为 下午1:03 2019/10/25
 * 项目名称 spring-boot-security
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@Builder
public class CaptchaUtil implements Serializable {

    private static final long serialVersionUID = 908094369091154079L;

    private static final Random RANDOM = new Random();

    /**
     * 默认验证码字符集
     */
    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    /**
     * 默认字符数量
     */
    private final Integer size;

    /**
     * 默认干扰线数量
     */
    private final Integer lines;

    /**
     * 默认宽度
     */
    private final Integer width;

    /**
     * 默认高度
     */
    private final Integer height;

    /**
     * 默认字体大小
     */
    private final Integer fontSize;

    /**
     * 默认字体倾斜
     */
    private final Boolean tilt;

    private final Color backgroundColor;


    /**
     * @return 生成随机验证码及图片
     * Object[0]：验证码字符串；
     * Object[1]：验证码图片。
     */
    public Captcha createImage() {
        StringBuilder sb = new StringBuilder();
        // 创建空白图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图片画笔
        Graphics2D graphic = image.createGraphics();
        // 设置抗锯齿
        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置画笔颜色
        graphic.setColor(backgroundColor);
        // 绘制矩形背景
        graphic.fillRect(0, 0, width, height);
        // 画随机字符
        Random ran = new Random();
        // 计算每个字符占的宽度，这里预留一个字符的位置用于左右边距
        int codeWidth = width / (size + 1);
        // 字符所处的y轴的坐标
        int y = height * 3 / 4;
        for (int i = 0; i < size; i++) {
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 初始化字体
            Font font = new Font(null, Font.BOLD + Font.ITALIC, fontSize);
            if (tilt) {
                // 随机一个倾斜的角度 -45到45度之间
                int theta = ran.nextInt(45);
                // 随机一个倾斜方向 左或者右
                theta = (ran.nextBoolean()) ? theta : -theta;
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.rotate(Math.toRadians(theta), 0, 0);
                font = font.deriveFont(affineTransform);
            }
            // 设置字体大小
            graphic.setFont(font);
            // 计算当前字符绘制的X轴坐标
            int x = (i * codeWidth) + (codeWidth / 2);
            // 取随机字符索引
            int n = ran.nextInt(CHARS.length);
            // 得到字符文本
            String code = String.valueOf(CHARS[n]);
            // 画字符
            graphic.drawString(code, x, y);
            // 记录字符
            sb.append(code);
        }
        // 画干扰线
        for (int i = 0; i < lines; i++) {
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 随机画线
            graphic.drawLine(ran.nextInt(width), ran.nextInt(height), ran.nextInt(width), ran.nextInt(height));
        }
        // 返回验证码和图片
        return new Captcha(sb.toString(), image);
    }

    /**
     * 随机取色
     */
    private Color getRandomColor() {
        return new Color(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Captcha implements Serializable {

        private static final long serialVersionUID = -4045096598706516582L;

        private String captcha;

        private Integer maxRetry = 3;

        private LocalDateTime expireTime = LocalDateTime.now().plusSeconds(60);

        private transient BufferedImage image;

        public Captcha(String captcha, BufferedImage image) {
            this.captcha = captcha;
            this.image = image;
        }

        public Captcha(String captcha, int expireIn, BufferedImage image) {
            this.captcha = captcha;
            this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
            this.image = image;
        }

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expireTime);
        }

    }


}
