#version 150

uniform sampler2D DiffuseSampler;
in vec2 texCoord;
uniform float IntensityAmount;
out vec4 fragColor;

void main() {
    // 获取纹理尺寸
    ivec2 texSize = textureSize(DiffuseSampler, 0);
    vec2 texelSize = vec2(1.0 / texSize.x, 1.0 / texSize.y);

    // 高斯模糊参数
    const int radius = 10;
    const float sigma = float(radius) / 3.0;
    const float twoSigmaSq = 2.0 * sigma * sigma;

    vec3 blurSumLinear = vec3(0.0);
    float totalWeight = 0.0;

    // 二维高斯采样
    for (int x = -radius; x <= radius; ++x) {
        for (int y = -radius; y <= radius; ++y) {
            // 计算当前采样位置
            vec2 offset = vec2(x, y) * texelSize;
            vec3 sampleColor = texture(DiffuseSampler, texCoord + offset).rgb;

            // 转换到线性空间
            vec3 linearColor = pow(sampleColor, vec3(2.2));

            // 计算高斯权重
            float distSq = float(x*x + y*y);
            float weight = exp(-distSq / twoSigmaSq);

            blurSumLinear += linearColor * weight;
            totalWeight += weight;
        }
    }

    // 归一化并转换回sRGB
    vec3 blurredColor = pow(blurSumLinear / totalWeight, vec3(1.0/2.2));

    // 原始颜色（sRGB空间）
    vec3 originalColor = texture(DiffuseSampler, texCoord).rgb;

    // 根据强度混合原色和模糊色
    vec3 finalColor = mix(originalColor, blurredColor, IntensityAmount);

    // 输出结果（强制alpha为1.0）
    fragColor = vec4(finalColor, 1.0);


    vec2 center = vec2(0.5);
    vec2 screenRatio = vec2(texSize.x/texSize.y, 1.0); // 适配屏幕比例
    float dist = length((texCoord - center) * screenRatio) * 1.4142;

    // 红边强度计算
    float edgeIntensity = smoothstep(0.3, 0.7, dist * 0.55)
    * pow(IntensityAmount * 2.0, 0.6)
    * (1.0 - exp(-dist * 4.0));

    // 红边颜色（sRGB空间直接混合）
    vec4 redEdge = vec4(
    0.8 + edgeIntensity * 0.3,  // R通道加强
    edgeIntensity * 0.05,       // 减少绿色干扰
    edgeIntensity * 0.05,       // 减少蓝色干扰
    edgeIntensity * 0.8         // 加强可见度
    );

    // 叠加混合（适配原有alpha处理）
    finalColor = mix(
    finalColor,
    redEdge.rgb,
    redEdge.a * (1.0 - smoothstep(0.2, 0.9, finalColor.r))
    );

    fragColor = vec4(clamp(finalColor, 0.0, 1.0), 1.0);
}