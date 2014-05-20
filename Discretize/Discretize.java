import java.util.*;  
import java.io.*;  
      
    public class Discretize  
    {
    	public static void main(String[] args) throws FileNotFoundException, IOException   
        {  
            double[][] arrayValues = new double[61][4];  
            //double[][] arrayval = new double[61][4];
            BufferedReader bufRdr  = new BufferedReader(new FileReader("C://Users/@m@r/Desktop/dataset.csv"));  
            String line = null;  
            int row = 0;  
            int col = 0;  
        
            //read each line of text file  
            while((line = bufRdr.readLine()) != null)  
            {  
                StringTokenizer st = new StringTokenizer(line,",");  
                while (st.hasMoreTokens())  
                {  
            //get next token and store it in the array
              System.out.println("Row and col = " + row + " " + col );       
              arrayValues[row][col] = Double.parseDouble(st.nextToken());
              col++;  
                }  
                row++;
                col = 0;
            }    
            bufRdr.close();
            
            Double max = maximumofAttributes(arrayValues)
            Double min = minimumofAttributes(arrayValues)
            int threshold = (max - min) / 10
            classified = datasetclassification(threshold,arrayValues[][],arrayvalues[][0])
            calculateEntropy((double[] classified)
            		
        }
    	
    	
		private static double maximumofAttribites(double[][] arrayValues)
    	        {
    	            double max = 0;
    	            foreach (double attribute in arrayValues)
    	            {
    	                if (attribute > max)
    	                    max = attribute;
    	            }
    	            return max;
    	        }
		Private static double minimumofAttributes(double[][] arrayvalues)
	        {
	            double min = 100;
	            foreach (double attribute in arrayValues)
	            {
	                if (arrayValues < min)
	                    min = attribute;
	            }
	            return min;
	        }
			
		Private static double datasetclassification(double threshold,double[] attributes,double[] classvalues)
	       {
	            double[,,] temp = new double[2,100,100];
	            int i = 0, j = 0, k = 0;
	            foreach (double attribute in attributes)
	            {
	                if (attribute < threshold)
	                {
	                    temp[0,i,0] = attribute;
	                    temp[0,i++,1] = classvalues[k++];
	                }
	                else
	                {
	                    temp[1,j,0] = attribute;
	                    temp[1,j++,1] = classvalues[k++];
	                }
	                temp[0, i, 1] = 12345;
	                temp[0, j, 1] = 12345;
	            }
	            return temp;
	        }
		public static Double calculateEntropy((double[] classifieddata) {
				  Map<String, Integer> map = new HashMap<String, Integer>();
				  // count the occurrences of each value
				  for (String sequence : values) {
				    if (!map.containsKey(sequence)) {
				      map.put(sequence, 0);
				    }
				    map.put(sequence, map.get(sequence) + 1);
				  }
				 
				  // calculate the entropy
				  Double result = 0.0;
				  for (String sequence : map.keySet()) {
				    Double frequency = (double) map.get(sequence) / values.size();
				    h1 -= frequency * (Math.log(frequency) / Math.log(2));
				  }
				  
				  for (int i = 0; i < classifieddata.Length; i++)
		            {
		                if (classifieddata[1,i,1] == 0)
		                {
		                    class3count++;
		                }
		                else
		                {
		                    class4count++;
		                }
		            }
		            x = (class3count) / ((class3count + class4count));
		            y = (class4count) / ((class3count + class4count));
		            w = (class3count + class4count) / (class1count + class2count + class3count + class4count);

		            h2 = -((x * Math.Log(x, 2)) + (y * Math.Log(y, 2)));


		            x = (class1count + class3count) / (class1count + class2count + class3count + class4count);

		            y = (class2count + class4count) / (class1count + class2count + class3count + class4count);

		            h = -((x * Math.Log(x, 2)) + (y * Math.Log(y, 2)));

		            informationgain = h- ((z*h1)+(w*h2));

		            return informationgain;
			}  
    }  