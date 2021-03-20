

package jogl2;
import java.lang.Math;
import com.jogamp.opengl.util.FPSAnimator;
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
public class Robot extends GLCanvas implements GLEventListener {
    // constants
    private static String TITLE = "Mr Robot";
    private static final int CANVAS_WIDTH = 800;  // width of the drawable
    private static final int CANVAS_HEIGHT = 600; // height of the drawable


    private int posX;
    private int posY;
    private int posZ;

    private double velX;
    private double velZ;
    private double velY;
    private double playerAngle;
    private double playerAngleZ;


    private int rotAngleY = 0;

    private int rotCountLegs = 5;
    private int rotCountArms = 3;

    private boolean move = false;
    public int count = 0;


    static final float cornerPositions [][] = {{ 1, 1, 0},{ 1, 3, 0}, {3,3,0}, {3,1,0}, {1,3,-2}, {1,1,-2}, {3,3,-2}, {3,1,-2}};
    static final float torsoPositions [][] = {{ -1, 2, 0},{ -1, 6, 0}, {3,6,0}, {3,2,0}, {-1,6,-2}, {-1,2,-2}, {3,6,-2}, {3,2,-2}};
    static final float legPositions [][] = {{ 1, 2, 0},{ 1, 6, 0}, {1.5f,6,0}, {1.5f,2,0}, {1,6,-2}, {1,2,-2}, {1.5f,6,-2}, {1.5f,2,-2}};
    static final float armPositions [][] = {{ 1, 2, 0},{ 1, 6, 0}, {1.5f,6,0}, {1.5f,2,0}, {1,6,-2}, {1,2,-2}, {1.5f,6,-2}, {1.5f,2,-2}};
    static final float floorPositions [][] = {{ -1000, 1, 0},{ 1000, 1, 0}, {1000,1,-1000}, {-1000,1,-1000}};

    static final float eyesPositions [][] = {{ 0, 1, 1},{ 1, 1, 0}, {1,1,-100}, {0,1,-100}};

    static final float colors[][] = {{0.6f, 0.6f, 0.6f}, {0.6f, 0.6f, 0.6f}, {0.6f, 0.6f, 0.6f}, {0.6f, 0.6f, 0.6f}, {0.6f, 0.6f, 0.6f},{0.6f, 0.6f, 0.6f}};
    static final float colorsObstacle[][] = {{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f},{0.0f, 0.0f, 0.0f}};

    static final float eyecolors[][] = {{1.0f, 0.0f, 0.0f}};
    static final float floorcolors[][] = {{0.0f, 1.0f, 0.0f}};


    // Setup OpenGL Graphics Renderer
    private GLU glu;  // for the GL Utility
    private GLUT glut = new GLUT();


    /** Constructor to setup the GUI for this Component */
    public Robot() {
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
        gl.glClearColor(0.0f, 0.0f, 1.0f, 0.0f); // set background (clear) color
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


    public void XLine(GL2 gl){
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3d(99.0,0.0,0.0);
        gl.glVertex3d(-99.0,0.0,0.0);
        gl.glEnd();

    }

    public void YLine(GL2 gl){
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3d(0.0,99.0,0.0);
        gl.glVertex3d(0.0,-99.0,0.0);
        gl.glEnd();
    }

    public void ZLine(GL2 gl){
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3d(0.0,0.0,99.0);
        gl.glVertex3d(0.0,0.0,-99.0);
        gl.glEnd();
    }

    public void drawSide(GL2 gl, int a, int b, int c, int d){
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3fv(colors[a],0);
        gl.glVertex3fv(cornerPositions[a],0);
        gl.glVertex3fv(cornerPositions[b],0);
        gl.glVertex3fv(cornerPositions[c],0);
        gl.glVertex3fv(cornerPositions[d],0);
        gl.glEnd();
    }

    public void drawSide2(GL2 gl, int a, int b, int c, int d){
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3fv(colorsObstacle[a],0);
        gl.glVertex3fv(cornerPositions[a],0);
        gl.glVertex3fv(cornerPositions[b],0);
        gl.glVertex3fv(cornerPositions[c],0);
        gl.glVertex3fv(cornerPositions[d],0);
        gl.glEnd();
    }

    public void drawSideFloor(GL2 gl, int a, int b, int c, int d){
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3fv(floorcolors[a],0);
        gl.glVertex3fv(floorPositions[a],0);
        gl.glVertex3fv(floorPositions[b],0);
        gl.glVertex3fv(floorPositions[c],0);
        gl.glVertex3fv(floorPositions[d],0);
        gl.glEnd();
    }

    public void drawFloor(GL2 gl){
        gl.glTranslated(0,-6,0);
        drawSideFloor(gl, 0,1,2,3);
        XLine(gl);
        YLine(gl);
        ZLine(gl);

    }

    public void drawEyesSquare(GL2 gl, int a, int b, int c, int d){
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3fv(eyecolors[0],0);
        gl.glVertex3fv(eyesPositions[a],0);
        gl.glVertex3fv(eyesPositions[b],0);
        gl.glVertex3fv(eyesPositions[c],0);
        gl.glVertex3fv(eyesPositions[d],0);
        gl.glEnd();
    }


    public void drawEyes(GL2 gl){

        drawEyesSquare(gl, 0,1,2,3);


    }
    public void drawSideBody(GL2 gl, int a, int b, int c, int d){
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3fv(colors[a],0);
        gl.glVertex3fv(torsoPositions[a],0);
        gl.glVertex3fv(torsoPositions[b],0);
        gl.glVertex3fv(torsoPositions[c],0);
        gl.glVertex3fv(torsoPositions[d],0);
        gl.glEnd();
    }

    public void drawSideLeg(GL2 gl, int a, int b, int c, int d){
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3fv(colors[a],0);
        gl.glVertex3fv(legPositions[a],0);
        gl.glVertex3fv(legPositions[b],0);
        gl.glVertex3fv(legPositions[c],0);
        gl.glVertex3fv(legPositions[d],0);
        gl.glEnd();
    }

    public void drawSideArm(GL2 gl, int a, int b, int c, int d){
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3fv(colors[a],0);
        gl.glVertex3fv(armPositions[a],0);
        gl.glVertex3fv(armPositions[b],0);
        gl.glVertex3fv(armPositions[c],0);
        gl.glVertex3fv(armPositions[d],0);
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

    public void drawObstacle(GL2 gl){
        gl.glTranslated(15,-5,-130);
        drawSide2(gl, 0,1,2,3);
        drawSide2(gl, 1,0,5,4);
        drawSide2(gl, 3,2,6,7);
        drawSide2(gl, 4, 5,7,6);
        drawSide2(gl, 5, 0,3,7);
        drawSide2(gl, 2, 1,4,6);
    }

    public void drawBody(GL2 gl){
        gl.glBegin(GL2.GL_POLYGON);
        drawSideBody(gl, 0,1,2,3);
        drawSideBody(gl, 1,0,5,4);
        drawSideBody(gl, 3,2,6,7);
        drawSideBody(gl, 4, 5,7,6);
        drawSideBody(gl, 5, 0,3,7);
        drawSideBody(gl, 2, 1,4,6);
        gl.glEnd();    }


    private double footAngle = 50;
    private double footAngle2 = -50;
    private double armAngle = 50;
    private double vinkelP;

    private double armAngle2 = -50;

    private double aboveground = 0.0;



    public void drawLeg(GL2 gl){
        gl.glBegin(GL2.GL_POLYGON);

            drawSideLeg(gl, 0,1,2,3);
            drawSideLeg(gl, 1,0,5,4);
            drawSideLeg(gl, 3,2,6,7);
            drawSideLeg(gl, 4, 5,7,6);
            drawSideLeg(gl, 5, 0,3,7);
            drawSideLeg(gl, 2, 1,4,6);
            gl.glEnd();    }


    public void drawArm(GL2 gl){
        gl.glBegin(GL2.GL_POLYGON);
        drawSideArm(gl, 0,1,2,3);
        drawSideArm(gl, 1,0,5,4);
        drawSideArm(gl, 3,2,6,7);
        drawSideArm(gl, 4, 5,7,6);
        drawSideArm(gl, 5, 0,3,7);
        drawSideArm(gl, 2, 1,4,6);
        gl.glEnd();    }

    public void drawCharacter(GL2 gl){
        gl.glPushMatrix();

        gl.glTranslated(posX, aboveground, posZ);
        gl.glRotated(rotAngleY, 0 ,1,0);
        gl.glTranslated(-2,-5,10);
        if(aboveground>0 && aboveground>=0)
      aboveground--;
        //head
       gl.glTranslated(0.0, aboveground, 0.0);
        drawCube(gl);

        //body
        gl.glTranslatef(1.0f, -5.0f, 0.0f);
        drawBody(gl);

        //legs


        gl.glPushMatrix();
        if(move == true){
            if(footAngle<=50 && footAngle>= -50){
                footAngle -= rotCountLegs;
                if(footAngle >50 || footAngle<-50){
                    rotCountLegs *= (-1);
                }
            }
            gl.glRotated(footAngle, 1, 0, 0);
        }
        gl.glTranslatef(-1.0f, -3.5f, 0.0f);
        drawLeg(gl);
        gl.glPopMatrix();



        gl.glPushMatrix();
        if(move == true){
            if(footAngle2<=50 && footAngle2>= -50){
                footAngle2 -= -rotCountLegs;
                if(footAngle2>=50 || footAngle2<=-50){
                    rotCountLegs *= (-1);
                }
            }
            gl.glRotated(footAngle2, 1, 0, 0);
        }
        gl.glTranslatef(0.5f, -3.5f, 0.0f);
        drawLeg(gl);
        gl.glPopMatrix();


        //arms
        gl.glPushMatrix();
        gl.glRotated(180,0,0,1);
        gl.glTranslatef(-0.2f, -7.5f, 0.0f);
        if(move == true){
            if(armAngle<=50 && armAngle>= -50){
                armAngle -= rotCountArms;
                if(armAngle>=50 || armAngle<=-50){
                    rotCountArms *= (-1);
                }
            }
            gl.glRotated(footAngle, 1, 0, 0);
        }        drawArm(gl);
        gl.glPopMatrix();

        //arms
        gl.glPushMatrix();
        gl.glRotated(180,0,0,1);
        gl.glTranslatef(-4.2f, -7.5f, 0.0f);
        if(move == true){
            if(armAngle2<=50 && armAngle2>= -50){
                armAngle2 -= -rotCountArms;
                if(armAngle2>=50 || armAngle2<=-50){
                    rotCountArms *= (-1);
                }
            }
            gl.glRotated(footAngle2, 1, 0, 0);
        }        drawArm(gl);
        gl.glPopMatrix();


        gl.glPopMatrix();
    }


    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
        gl.glLoadIdentity();  // reset the model-view matrix




        playerAngle = 0;
        velX = 0;
        velZ = 0;

        playerAngle = Math.cos(Math.toRadians(rotAngleY));;
        playerAngleZ = Math.sin(Math.toRadians(rotAngleY));


        double rot = Math.toRadians(rotAngleY);
        double dist = 40;
        double rotX = Math.sin(rot)*dist;
        double rotZ = Math.cos(rot)*dist;

        glu.gluLookAt(posX+rotX, posY+10, posZ+rotZ, posX, posY, posZ, 0, 1, 0);


        gl.glPushMatrix();
        drawFloor(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        drawObstacle(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(0,7,0);
        drawCharacter(gl);
        gl.glPopMatrix();


    }

    /**
     * Called before the OpenGL context is destroyed. Release resource such as buffers.
     */
    public void dispose(GLAutoDrawable drawable) { }

    private class RotateKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            double speed = 15;
            double rot = Math.toRadians(rotAngleY);
            if (e.getKeyChar() == 'a') {
                rotAngleY += 4;
                //velX = playerAngle*(posZ);;
                Robot.this.repaint();//repaint our canvas
            }
            if (e.getKeyChar() == 'd') {
                rotAngleY -= 4;
                // velX = playerAngle*(posZ);;
                Robot.this.repaint();//repaint our canvas
            }
            if (e.getKeyChar() == 'w') {
                move = true;
                posX -= Math.sin(rot) * speed;//
                posZ -= Math.cos(rot) * speed;//

                // velZ += playerAngleZ;//

                Robot.this.repaint();//repaint our canvas
            }
            if (e.getKeyChar() == 's') {
                move = true;
                posX += Math.sin(rot) * speed;//
                posZ += Math.cos(rot) * speed;//
                //move = true;
                //velZ -= playerAngleZ;//


                Robot.this.repaint();//repaint our canvas
            }
            if (e.getKeyChar() == ',') {
                aboveground += 5.0;
                Robot.this.repaint();//repaint our canvas
                count++;
            }
            if (e.getKeyChar() == '.') {
                if (count > 0) {

                }
                Robot.this.repaint();//repaint our canvas
            }

        }

        public void keyReleased(KeyEvent e) {
            double speed = 15;
            double rot = Math.toRadians(rotAngleY);
            if (e.getKeyChar() == 'a') {

                //velX = playerAngle*(posZ);;
                Robot.this.repaint();//repaint our canvas
            }
            if (e.getKeyChar() == 'd') {

                // velX = playerAngle*(posZ);;
                Robot.this.repaint();//repaint our canvas
            }
            if (e.getKeyChar() == 'w') {
                move = false;
                // velZ += playerAngleZ;//

                Robot.this.repaint();//repaint our canvas
            }
            if (e.getKeyChar() == 's') {
                move = false;

                //move = true;
                //velZ -= playerAngleZ;//


                Robot.this.repaint();//repaint our canvas
            }

            }

        }



    /** The entry main() method to setup the top-level JFrame with our OpenGL canvas inside */
    public static void main(String[] args) {
        GLCanvas canvas = new Robot();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame
        frame.getContentPane().add(canvas);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//stop program
        frame.setTitle(TITLE);
        frame.pack();
        frame.setVisible(true);
        FPSAnimator animator = new FPSAnimator(canvas, 30);
        animator.start();
    }
}
