
import java.awt.*;
import javax.swing.*;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * NeHe Lesson #2 (JOGL 2 Port): Basic 2D Shapes
 * @author Tomas Holt, based on code from Hock-Chuan Chua (May 2012)
 * @version October 2016
 */

/* Main class which extends GLCanvas. This means that this is a OpenGL canvas.
   We will use OpenGL commands to draw on this canvas.
   This implementation has no animation or user input.
*/
public class Kube extends GLCanvas implements GLEventListener {
    // constants
    private static String TITLE = "Kube med viewports";
    private static final int CANVAS_WIDTH = 600;  // width of the drawable
    private static final int CANVAS_HEIGHT = 600; // height of the drawable


    private int rotAngleX;
    private int rotAngleY;
    private int rotAngleZ;

    static final float cornerPositions [][] = {{ 1, 1, 0},{ 1, 3, 0}, {3,3,0}, {3,1,0}, {1,3,-2}, {1,1,-2}, {3,3,-2}, {3,1,-2}};
    static final float colors[][] = {{3, -8, 4}, {1, 1, 1}, {5,4,-2}, {1,-3,-8}, {-3,3,-2},{-3,-1,2}};


    // Setup OpenGL Graphics Renderer
    private GLU glu;  // for the GL Utility
    private GLUT glut = new GLUT();


    /** Constructor to setup the GUI for this Component */
    public Kube() {
        this.addGLEventListener(this);
        this.addKeyListener(new RotateKeyListener()); //listener for keyboard

    }

// ------ Implement methods declared in GLEventListener (init,reshape,display,dispose)

    /**
     * Called immediately after the OpenGL context is initialized. Can be used
     * to perform one-time initialization. Run only once.
     */
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();      // get the OpenGL graphics context
        glu = new GLU();                         // get GL Utilities
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
        gl.glEnable(GL2.GL_DEPTH_TEST);           // enables depth testing
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // best perspective correction
    }

    /**
     * Handler for window re-size event. Also called when the drawable is
     * first set to visible
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context

        if (height == 0) height = 1;   // prevent divide by zero
        float aspect = (float)width / height;

        //Set the view port (display area) to cover the entire window
       // gl.glViewport(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL2.GL_PROJECTION);  // choose projection matrix
        gl.glLoadIdentity();             // reset projection matrix
        glu.gluPerspective(45.0, aspect, 0.1, 1000.0); // fovy, aspect, zNear, zFar

        // Enable the model-view transform
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); // reset
    }

    /**
     * Called by OpenGL for drawing
     */



    public void drawSide(GL2 gl, int a, int b, int c, int d){
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3fv(colors[a],0);
        gl.glVertex3fv(cornerPositions[a],0);
        gl.glVertex3fv(cornerPositions[b],0);
        gl.glVertex3fv(cornerPositions[c],0);
        gl.glVertex3fv(cornerPositions[d],0);
        gl.glEnd();
    }

    public void drawCube(GL2 gl){

        drawSide(gl, 0,1,2,3);
        drawSide(gl, 1,0,5,4);
        drawSide(gl, 3,2,6,7);
        drawSide(gl, 4, 5,7,6);
        drawSide(gl, 5, 0,3,7);
        drawSide(gl, 2, 1,4,6);
    }


        public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
        gl.glLoadIdentity();  // reset the model-view matrix


            glu.gluLookAt(rotAngleX, rotAngleY, rotAngleZ, 5, 0, -20, 0, 1, 0);

        gl.glViewport(0,0 ,CANVAS_WIDTH/2, CANVAS_HEIGHT/2);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -3.0f, -10.0f);
        gl.glRotated( 55.0f, 1.0f, -4.1, 2.5 );
        drawCube(gl);
        gl.glPopMatrix();

        gl.glViewport(CANVAS_WIDTH/2, 0 ,CANVAS_WIDTH/2, CANVAS_HEIGHT/2);
        gl.glPushMatrix();
        gl.glTranslatef(5.0f, -3.0f, -10.0f);
        gl.glRotated( 55.0f, 0.0f, 1.5, 1.5 );
        drawCube(gl);
        gl.glPopMatrix();

        gl.glViewport(CANVAS_WIDTH/2, CANVAS_HEIGHT/2 ,CANVAS_WIDTH/2, CANVAS_HEIGHT/2);
        gl.glPushMatrix();
        gl.glTranslatef(5.0f, 0.0f, -15.0f);
        gl.glRotated( 130.0f, 1.5f, 0.0, 1.0 );
        drawCube(gl);
        gl.glPopMatrix();

        gl.glViewport(0, CANVAS_HEIGHT/2,CANVAS_WIDTH/2, CANVAS_HEIGHT/2);
        gl.glPushMatrix();
        gl.glTranslatef(5.0f, 0.0f, -10.0f);
        gl.glRotated( 130.0f, 0.0f, 1.5, 1.0 );
        drawCube(gl);
        gl.glPopMatrix();

    }

    /**
     * Called before the OpenGL context is destroyed. Release resource such as buffers.
     */
    public void dispose(GLAutoDrawable drawable) { }

    private class RotateKeyListener extends KeyAdapter{
        public void keyPressed(KeyEvent e) {
            if(e.getKeyChar() == 'a') {
                rotAngleX -= 10;//
                Kube.this.repaint();//repaint our canvas
            } else if(e.getKeyChar() == 'd'){
                rotAngleX += 10;//
                Kube.this.repaint();//repaint our canvas
            } else if(e.getKeyChar() == 'w'){
                rotAngleZ += 10;//
                Kube.this.repaint();//repaint our canvas
            } else if(e.getKeyChar() == 's'){
                rotAngleZ -= 10;//
                Kube.this.repaint();//repaint our canvas
            } else if(e.getKeyChar() == ','){
                rotAngleY += 10;//
                Kube.this.repaint();//repaint our canvas
            }
            else if(e.getKeyChar() == '.'){
                rotAngleY -= 10;//
                Kube.this.repaint();//repaint our canvas
            }



        }


    }

    /** The entry main() method to setup the top-level JFrame with our OpenGL canvas inside */
    public static void main(String[] args) {
        GLCanvas canvas = new Kube();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame
        frame.getContentPane().add(canvas);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//stop program
        frame.setTitle(TITLE);
        frame.pack();
        frame.setVisible(true);
    }
}
