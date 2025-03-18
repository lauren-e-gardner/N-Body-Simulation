public class Test {
    public static void main(String[] args) {

        // Parse command-line arguments
        double tau = Double.parseDouble(args[0]);
        double changeTau = Double.parseDouble(args[1]);
        double g = 6.67e-11;
        double distance;

        // Read universe from standard input
        int bodies = StdIn.readInt();
        double radius = StdIn.readDouble();
        double[] px = new double[bodies];
        double[] py = new double[bodies];
        double[] vx = new double[bodies];
        double[] vy = new double[bodies];
        double[] mass = new double[bodies];
        String[] picture = new String[bodies];

        for (int i = 0; i < bodies; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            picture[i] = StdIn.readString();
        }

        // Initialize standard drawing
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();

        // Play music on standard audio
        StdAudio.play("2001.wav");

        // Simulate the universe - loop:
        for (double j = 0.0; j < tau; j += changeTau) {
            double[] fx = new double[bodies * (bodies - 1)];
            double[] fy = new double[bodies * (bodies - 1)];

            // 3 5A. Calculate the net force on each body.
            if (bodies == 1) {
                StdDraw.clear();
                StdDraw.picture(0, 0, "starfield.jpg");
                vx[0] = vx[0] + (0 / mass[0]) * changeTau;
                vy[0] = vy[0] + (0 / mass[0]) * changeTau;
                px[0] = px[0] + (vx[0] * changeTau);
                py[0] = py[0] + (vy[0] * changeTau);

                // 2 5C. Draw the universe.
                StdDraw.picture(px[0], py[0], picture[0]);

                StdDraw.show();
                StdDraw.pause(20);
            }
            else {
                for (int i = 0; i < bodies; i++) {
                    for (int d = 0; d < bodies; d++) {
                        if (i != d) {
                            double force;
                            double pxSquared = Math.pow(px[d], 2) - Math.pow(px[i], 2);
                            double pySquared = Math.pow(py[d], 2) - Math.pow(py[i], 2);

                            distance = pxSquared + pySquared;

                            force = (g * mass[i] * mass[d]) / distance;

                            if (pxSquared < 0)
                                fx[i] += (force * (px[d] - px[i])) /
                                        (Math.sqrt(Math.abs(distance)) * -1);
                            else
                                fx[i] += (force * (px[d] - px[i])) /
                                        (Math.sqrt(Math.abs(distance)));
                            if (pySquared < 0)
                                fy[i] += (force * (py[d] - py[i])) /
                                        (Math.sqrt(Math.abs(distance)) * -1);
                            else
                                fy[i] += (force * (py[d] - py[i])) /
                                        (Math.sqrt(Math.abs(distance)));

                        }
                    }
                }


                // 1 5B. Update the velocities and positions.
                StdDraw.clear();
                StdDraw.picture(0, 0, "starfield.jpg");
                for (int k = 0; k < bodies; k++) {
                    vx[k] = vx[k] + ((fx[k]) / mass[k]) * changeTau;
                    vy[k] = vy[k] + ((fy[k]) / mass[k]) * changeTau;
                    px[k] = px[k] + (vx[k] * changeTau);
                    py[k] = py[k] + (vy[k] * changeTau);
                }
                // 2 5C. Draw the universe.
                for (int m = 0; m < bodies; m++) {
                    StdDraw.picture(px[m], py[m], picture[m]);
                }
                StdDraw.show();
                StdDraw.pause(20);
            }
        }
        StdOut.printf("%d\n", bodies);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], picture[i]);
        }
    }

}

