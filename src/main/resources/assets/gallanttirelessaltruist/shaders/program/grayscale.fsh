#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

uniform float IntensityAmount;

out vec4 fragColor;

void main(){
    vec4 color = texture(DiffuseSampler, texCoord);

    // 转换sRGB 2 Linear
    vec3 linearColor = pow(color.rgb, vec3(2.2));

    // 灰度
    float grayLinear = dot(linearColor, vec3(0.2126, 0.7152, 0.0722));

    // 转换回sRGB空间
    vec3 grayColor = pow(vec3(grayLinear), vec3(1.0/2.2));

    vec3 finalColor = mix(color.rgb, grayColor, IntensityAmount * 0.4);

    finalColor = finalColor * (1 + 0.16 * IntensityAmount);  // 亮度
//    finalColor = clamp(finalColor, 0.0, 1.0);  // 防止过曝

    // 强制alpha为1.0，避免透明混合问题
    fragColor = vec4(finalColor, 1.0);

//    fragColor = vec4(grayColor, 1.0);
}