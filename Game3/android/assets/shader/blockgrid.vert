

attribute vec3 a_position;

//varying vec2 v_position;

uniform mat4 u_projTrans;

void main() {
    //v_position = a_position.xy;
    gl_Position = u_projTrans * vec4(a_position, 1.0);
}