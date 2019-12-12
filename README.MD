   

   
   
    /**
     * 验证码请求参数的名称
     */
    private String paramName = "imageCode";

    /**
     * 验证路径
     */
    private Set<String> checkPaths = new HashSet<>();

    /**
     * 过期时间,单位是秒
     */
    private Integer expireTime = 60;

    /**
     * 默认字符数量
     */
    private Integer size = 4;

    /**
     * 默认干扰线数量
     */
    private Integer lines = 2;

    /**
     * 默认宽度
     */
    private Integer width = 120;

    /**
     * 默认高度
     */
    private Integer height = 35;

    /**
     * 默认字体大小
     */
    private Integer fontSize = 25;

    /**
     * 默认字体倾斜
     */
    private Boolean tilt = true;

    /**
     * 背景颜色
     */
    private Color backgroundColor = Color.LIGHT_GRAY;