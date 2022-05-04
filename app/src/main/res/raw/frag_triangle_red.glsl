#version 300 es
precision mediump float;
out vec4 FragColor;
in vec4 vertexColor;// 从顶点着色器传来的输入变量（名称相同、类型相同），使用关键字in

void main() {
    FragColor = vertexColor;
}