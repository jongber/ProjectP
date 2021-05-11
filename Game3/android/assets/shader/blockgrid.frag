
#ifdef GL_ES
precision mediump float;
#endif

uniform vec4 u_axisColor;
uniform float u_lineWidth;

varying vec3 v_position;


void main() {

    float test = u_lineWidth;
    if (v_position.x < 100.0f) {
        gl_FragColor = u_axisColor;
    }
    else {
        gl_FragColor = vec4(v_position, 1.0f);
    }
}