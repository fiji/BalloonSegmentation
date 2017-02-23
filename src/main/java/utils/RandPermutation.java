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
package utils;
/*********************************************************************
 * Version: January, 2008
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


public // Generating random permutations.
// used because for searching division planes through neighbouring relation
// uses rendomness because we are not searching through the entire set of combinations
class RandPermutation {
    private static int[] source;

    // constructor: generate the ordered list to be permutation
    public RandPermutation(int n)
    {
	source = new int[n];
	for (int i=0;i<n;i++)
	{
		source[i] = i;
	}
    }


    // It produces the next random permutation
    public int[] next(){
		int[] b = (int[])source.clone();
		for (int k = b.length - 1; k > 0; k--) {
		    int w = (int)Math.floor(Math.random() * (k+1));
		    int temp = b[w];
		    b[w] = b[k];
		    b[k] = temp;
		}
		return b;
    }
}
