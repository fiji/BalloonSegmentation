/*-
 * #%L
 * Balloon Segmentation plugin for Fiji.
 * %%
 * Copyright (C) 2012 - 2017 Fiji development team.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package sc.fiji.balloonSegmentation.utils;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

import java.util.Vector;


/*********************************************************************
 * Version: May, 2008
 ********************************************************************/

/*********************************************************************
 * Lionel Dupuy
 * SCRI
 * EPI program
 * Invergowrie
 * DUNDEE DD2 5DA
 * Scotland
 * UK
 *
 * Lionel.Dupuy@scri.ac.uk
 * http://www.scri.ac.uk/staff/lioneldupuy
 ********************************************************************/


public // Obtain a slice perpendicular to the confocal plane along a collumn of cells.
class CenterlineSlice {
	public ImagePlus i1;				// reference image
	private ImageStack stack1;			// related stack
    public Vector Points;				// points defining the path along which to slice the image
    private int nx,ny,nz;
	byte[] r,g,b;						// red green blue

    public CenterlineSlice (ImagePlus imp)
    {
	i1 = imp;
		stack1 = i1.getStack();
		ImageProcessor ip1 = stack1.getProcessor(1);
		nx = ip1.getWidth();
		ny = ip1.getHeight();
		nz = i1.getStackSize();

		r = new byte[nx*ny];
	    g = new byte[nx*ny];
	    b = new byte[nx*ny];

	    Points = new Vector();
    }

    public void GenerateSlice()
    {
		ColorProcessor cp = new ColorProcessor(Points.size(), nz);

		for (int j=0;j<nz;j++)
		{
			ColorProcessor ip1 = (ColorProcessor)(stack1.getProcessor(j+1));
	        ip1.getRGB(r,g,b);
		for (int i=0;i<Points.size();i++)
		{
			double x = ((double[])(Points.get(i)))[0];
			double y = ((double[])(Points.get(i)))[1];
			int[] pix = AveragePixelValue(x, y);
				cp.putPixel(i,j, (((int)pix[0] & 0xff) <<16)+ (((int)pix[1] & 0xff) << 8) + ((int)pix[2] & 0xff));
		}

	}
		ImagePlus imp_slice =  new ImagePlus("Projection",cp);
		imp_slice.show();
    }

    public void getPoints(int[][] LIST)
    {
	int N = LIST.length;
	IJ.log("  " + N);
	Points.clear();
	for (int i=1;i<N;i++)
	{
		int x1 = LIST[i-1][0];
		int y1 = LIST[i-1][1];
		int x2 = LIST[i][0];
		int y2 = LIST[i][1];
		double L = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		double vx = (x2-x1)/L;
		double vy = (y2-y1)/L;
		IJ.log("    " + i + "  /  " + L);
		for (int j=0;j<(int)L+1;j++)
		{
			IJ.log("          " + j);
			double[] xy = {x1+j*vx,x2+j*vy};
			Points.add(xy);
		}
	}
    }
    private int[] AveragePixelValue(double x, double y)
    {
	int i = (int)x;
	int j = (int)y;
	int l = i+j*nx;
	int[] pix = {r[l], g[l],b[l]};
	return pix;
    }
}
