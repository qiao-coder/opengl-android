#version 300 es
precision mediump float;
out vec4 FragColor;
uniform vec4 ourColor;// 在OpenGL程序代码中设定这个变量

void main(){
    FragColor = ourColor;
}