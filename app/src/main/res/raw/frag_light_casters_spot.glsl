#version 300 es
precision mediump float;

struct Material {
    sampler2D diffuse;
    sampler2D specular;
    float shininess;
};

uniform Material material;

struct Light {
    vec3 position;
    vec3  direction;
    float cutOff;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;

    float constant;
    float linear;
    float quadratic;
};

uniform Light light;



out vec4 FragColor;

uniform vec3 lightColor;
in vec3 Normal;
uniform vec3 lightPos;
in vec3 FragPos;
uniform vec3 viewPos;
in vec2 TexCoords;

void main() {
    vec3 lightDir = normalize(light.position - FragPos);

    // 检查是否在聚光灯内
    float theta = dot(lightDir, normalize(-light.direction));

    // 记住，点积返回的是一个余弦值而不是角度值。theta传的也是角度的余弦值。所以这里使用’>‘
    if (theta > light.cutOff) {
        // 环境光
        vec3 ambient = light.ambient * vec3(texture(material.diffuse, TexCoords));

        // 漫反射
        vec3 norm = normalize(Normal);
        float diff = max(dot(norm, lightDir), 0.0);
        vec3 diffuse = light.diffuse * diff * vec3(texture(material.diffuse, TexCoords));

        // 镜面光
        vec3 viewDir = normalize(viewPos - FragPos);
        vec3 reflectDir = reflect(-lightDir, norm);
        float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
        vec3 specular = light.specular * spec * vec3(texture(material.specular, TexCoords));

        // 衰弱
        float distance    = length(light.position - FragPos);
        float attenuation = 1.0 / (light.constant + light.linear * distance + light.quadratic * (distance * distance));

        // 从环境光中移除衰减，否则在很远的距离内，由于else分支中的环境光，聚光灯内部的光线会比聚光灯外部的光线更暗。
        // ambient  *= attenuation;
        diffuse   *= attenuation;
        specular *= attenuation;

        vec3 result = ambient + diffuse + specular;
        FragColor = vec4(result, 1.0);
    } else {
        // 使用环境光，让场景在聚光之外时不至于完全黑暗
        FragColor  = vec4(light.ambient * vec3(texture(material.diffuse, TexCoords)), 1.0);
    }
}