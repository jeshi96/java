import java.util.ArrayList;

public class QuickSort {

	public static void main(String[] args) {
		ArrayList<String> names = new ArrayList<String>();
		names.add("hi");
		names.add("lo");
		names.add("what");
		names.add("hey");
		names.add("this");
		names.add("legit");
		names.add("jess");
		System.out.println(quickSort(names));
	}
	
	public static ArrayList<String> quickSort(ArrayList<String> names){
		if(names.size()<=1)//if there is only one element left
			return names;//return the ArrayList (nothing to sort)
		int leftI=0;//initialize left index
		int rightI=names.size()-1;//initialize right index
		String left=names.get(leftI);//initialize left name
		String right=names.get(rightI);//initialize right name
		String pointer=left;//initialize pointer as the first name
		int pointerI=leftI;//initialize pointer index
		int rOL=1;//initialize variable to check whether to go through the ArrayList from the left or the right (start with right)
		while(rightI>leftI){//while there are still more names to check
			if(rOL==1){//if going from the right
				if(names.get(rightI).compareTo(pointer)<0){//if there is a value less than the pointer
					names.set(pointerI, right);//set the value at the place where the pointer was
					pointerI=rightI;//set the value's index as the pointer's new index
					rOL=0;//check from the left
				}//end if
				rightI--;//go to the next name (to the left)
				right=names.get(rightI);//go to the next name (to the left)
			}//end if
			else{//else if going from the left
				if(names.get(leftI).compareTo(pointer)>0){//if there is a value greater than the pointer
					names.set(pointerI, left);//set the value at the place where the pointer was
					pointerI=leftI;//set the value's index as the pointer's new index
					rOL=1;//check from the right
				}//end if
				leftI++;//go to the next name (to the right)
				left=names.get(leftI);//go to the next name (to the right)
			}//end else
		}//end while
		names.set(pointerI, pointer);//set the pointer in the last remaining spot
		//initialize ArrayLists to split up and quickSort the right and left parts of the names ArrayList
		ArrayList<String> leftNames=new ArrayList<String>();
		ArrayList<String> rightNames=new ArrayList<String>();
		for(int i=0;i<pointerI;i++){//for the left half of names
			leftNames.add(names.get(i));//set to leftNames ArrayList
		}//end for
		for(int i=pointerI+1;i<names.size();i++){//for the right half of names
			rightNames.add(names.get(i));//set to rightNames ArrayList
		}//end for
		names.clear();//clear names
		names.addAll(quickSort(leftNames));//quickSort the left half and add to names
		names.add(pointer);	//append pointer to names
		names.addAll(quickSort(rightNames));//quickSort the right half and add to names
		return names;//return the sorted names
	}//end method
}//end class
