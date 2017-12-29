package project1;
import java.util.Iterator;


public class MusicLinkedList implements MusicList {


	private float sampleRate;
	private float duration;
	private int numChannels;
	private int numSamples = 0;
	
	private Link head, tail, current, prevCol, colHead, newColHead, firstNode, lastNode = null;


	public MusicLinkedList(float sampleRate, int numChannels) {
		this.sampleRate = sampleRate;
		this.numChannels = numChannels;	
	}

	@Override
	public int getNumChannels() {
		return numChannels;
	}

	@Override
	public float getSampleRate() {
		return sampleRate;
	}

	@Override
	public int getNumSamples() {
		return numSamples;
	}

	@Override
	public float getDuration() {
		duration = getNumSamples()/getSampleRate();
		return duration;
	}
	
	
	/** 
	 * Adds an echo effect to the soundList. 
	 * Get delay, continually loop to find first node to start delay. While delay Link is not null
	 * loop through and set sample to next sample with delay for echo effect
	 */
	@Override
	public void addEcho(float delay, float percent) {
		
		float delayStart = delay*sampleRate;
		Link delayStartNode = head;
		
		for(int i = 0 ; i < delayStart; i++){
			
			delayStartNode = delayStartNode.getNextX();
		}
		
		Link headX = head;
		
		while(delayStartNode != null){
			
			Link headY = headX;
			Link delayStartY = delayStartNode;
			
			while(delayStartY != null){
				
				delayStartY.setElem(delayStartY.elem() + headY.elem()*percent);
				delayStartY = delayStartY.getNextY();
				headY = headY.getNextY();
				
			}
			
			delayStartNode = delayStartNode.getNextX();
			headX = headX.getNextX();
			
		}

	}
	
	
	/**  Add the waveform of clipToCombine to this clip. 
	* If allowClipping is true, clip all samples in the range -1, 1. 
	* If allowClipping is false, iterate through entire structure, save biggest
	* sample as max. Iterate through again, and save sample as ratio of max */ 
	@Override
	public void combine(MusicLinkedList clipToCombine, boolean allowClipping) {
			

		Link temp1 = head;
		current = head;
		float max = 1.0f;
		float min = -1.0f;
			
		if(allowClipping) {
				
			Iterator<float[]> iterator1 = clipToCombine.iterator();
				
			while(current != null) {
					
				temp1 = current;
				float[] arr = iterator1.next();
					
				for(int i = 0; i < arr.length; i++) {
						
					if((temp1.elem() + arr[i]) > max) {
						temp1.setElem(max);
							
					} else if((temp1.elem() + arr[i]) < min) {
						temp1.setElem(min);
							
					} else {
						temp1.setElem(temp1.elem() + arr[i]);
					}
						
					if(temp1.getNextY() != null) {
						temp1 = temp1.getNextY();
					}
						
				} // end for loop
					
				current = current.getNextX();
			} // end while
		}

		if(!allowClipping) {
				
			float newMax = 0.0f; 
			Link tmp = head;
			
			InnerIterator iterator = new InnerIterator();
			float[] eachSample = new float[getNumChannels()];
				
			while(iterator.hasNext()) {
				
				eachSample = iterator.next();
				
				for(int i = 0; i < eachSample.length; i++) {
					
					if(eachSample[i]*2 > newMax) {
						newMax = eachSample[i]*2;
					}
					
				}
				
				tmp = tmp.getNextX();
			}
			
			tmp = head;
			InnerIterator iterator2 = new InnerIterator();
			
			while(iterator2.hasNext()) {
				
				eachSample = iterator2.next();
				
				for(int i = 0; i < eachSample.length; i++) {
					
					eachSample[i] = eachSample[i]/newMax;
				}
				
				tmp = tmp.getNextX();
			}
			
		} // end no clip

	} // end method
		
		
	/** Clips the SoundList by throwing away all samples before the startTime (in seconds), 
	 * and after the duration (in seconds). So, if the SoundList was 6 seconds long, and we called clip(4,2), 
	 * the new SoundList would be 2 seconds long (and would consist of samples from second 4 to second 6 
	 * of the original SoundList). */
	@Override
	public void clip(float startTime, float duration) {

		float sampleDuration = 1/sampleRate;
		float startSample = startTime*sampleRate;
		float endSample = (startTime + duration)*sampleRate;

		int startSampleInt = 1;
		
		startSample = startSample/startSampleInt;
		startSampleInt = (int)startSample/1;
		
		Link beginningOfSample = head;
		Link sampleHead = null;
		Link sampleTail = null;
		
		for(int i = 0; i < startSampleInt; i++){
			
			sampleHead = beginningOfSample;
			beginningOfSample = beginningOfSample.getNextX();
		}
		
		sampleTail = beginningOfSample;
		
		for(int i = (int)startSample; i < endSample; i++){
			
			sampleTail = sampleTail.getNextX();			
		}
		
		Link yIter = sampleTail;
		
		while(yIter != null){
			
			yIter.setNextX(null);
			yIter = yIter.getNextY();
			
		}
		
		head = sampleHead;
		tail = sampleTail; 
		
	}

	
	/** Splice (join) clipToSplice into this clip, starting at startSpliceTime 
	 * Go through list until specific time (beginning of sample). Iterate through clip
	 * samples and add them. Then set them to initial sample list (MusicLinkedList). */
	@Override
	public void spliceIn(float startSpliceTime, MusicList clipToSplice) {
		
		
		float beginningOfSample = startSpliceTime*sampleRate;
		int startSampleInt = 1;
		beginningOfSample /= startSampleInt;
		
		Link current = head;
		Link endOfSplice = null;

		startSampleInt = (int)beginningOfSample/1;
		 
		for(int i = 0; i < beginningOfSample; i++){
			 
			current = current.getNextX();
		}
		 
		tail = current;
		endOfSplice = tail.getNextX();
		 
		Iterator<float[]> clipIter = clipToSplice.iterator();
		 
		for(int i = 0; i < clipToSplice.getNumSamples(); i++){
			 
			float[] splicedSamples = clipIter.next();
			addSample(splicedSamples);
		}
		 
		Link temp = tail;
		 
		while(temp != null){
			 
			temp.setNextX(endOfSplice);
			temp = temp.getNextY();
			endOfSplice = endOfSplice.getNextX();
			 
		}
		 
	}


	/**
	 *  Reverses the musicLinkedList */
	@Override
	public void reverse() {

		Link previous = null;
		Link current = head;
		Link newLink = null;

		while(current != null) {
			
			if(getNumChannels() > 1) {
				
				newLink = current.getNextY();
				current.setNextY(previous);
				
			} else {
				
				newLink = current.getNextX();
				current.setNextX(previous);
			}
			
			previous = current;
			current = newLink;
			
		}
	}


	/** Changes sample rate
	 * @parameter percentageChange - is multiplied to the sampleRate to change sampleRate
	 */
	@Override
	public void changeSpeed(float percentChange) {
		sampleRate *= percentChange;
	}


	/** Adds a sample to the end of the SoundList. If the SoundList is not single-channel, 
	 * this method should throw and IllegalArgument exception 
	 * @parameter sample - sample rate per second */
	@Override
	public void addSample(float sample) {
		
		numSamples++;

		if(numChannels > getNumChannels()) {
			throw new IllegalArgumentException();
		}
		
		if(head != null) {
			
			Link newNode = new Link(sample, null, null);
			current.setNextX(newNode);
			current = newNode;
			
		}
		
		if(head == null) {
			
			Link newNode = new Link(sample, null, null);
			head = tail = newNode;
			current = newNode;
			
		}
	}

	/** Adds samples to the end of the SoundList. If the length of the sample array is not 
	 * the same as the number of channels in the SampleList, throw an IllegalArgument exception
	 * @parameter sample[] - 
	 */
	@Override
	public void addSample(float[] sample) {

		numSamples++;
		int len = sample.length;

		if(len != getNumChannels()) {
			System.out.println("ERROR!");
			throw new IllegalArgumentException();
		}
		
		if(head != null) {

			Link tmp = tail;
			int j = 0;

			
			/* Create list of sample array size */
			while(j < sample.length) {
				
				Link newNode = new Link(sample[j], null, null);
				
				tmp.setNextX(newNode);

				if(tmp.getNextY() != null) {
					tmp = tmp.getNextY();
				}
				
				if(j == 0) {
					tail = newNode;
				}
				
				if(j > 0) {
					current.setNextY(newNode);
				}
				
				current = newNode;
				j++;
				
			}
		} /* end of head not null */

		/* build the first linked list */
		else {
			
			for(int i = 0; i < sample.length; i++) {
				
				if(i > 0) {
					
					Link newNodes = new Link(sample[i], null, null);
					current.setNextY(newNodes);
					current = newNodes;
					
				} else {
					
					Link newNode = new Link(sample[i], null, null);
					head = tail = newNode;
					current = newNode;
					prevCol = newNode;
					
				}
			}
		}
		
	}


	/** Combine all channels into one. If allowClipping is true, 
	 * clip all samples in the range -1, 1. If allowClipping is false, 
	 * rescale resulting waveform to be in the range -1, 1 
	 * @parameter - allowClipping - determines if clipping is allowed or not
	 */
	@Override
	public void makeMono(boolean allowClipping) {

		float max = 0;
		current = head;
		Link tmp = current;

		InnerIterator iterator = new InnerIterator();
		
		float[] eachSample = new float[getNumChannels()];
		float totalSample = 0.0f;

		while(iterator.hasNext()) {
			
			eachSample = iterator.next();
			
			for(int i = 0; i < eachSample.length; i++) {
				totalSample += eachSample[i];
			}
			
			if(totalSample > max) {
				max = totalSample;
			}
			
			tmp.setElem(totalSample);
			totalSample = 0.0f;
			tmp = tmp.getNextX();
			
		}
		
		if(!allowClipping) {

			current = head;
			tmp = current;
			
			InnerIterator iterator2 = new InnerIterator();

			while(iterator2.hasNext()) {

				eachSample = iterator2.next();

				for(int i = 0; i < eachSample.length; i++) {
					
					if(i == 0) {
						
						float theSample = tmp.elem();
						theSample = theSample/max;
						
						tmp.setElem(theSample);
						tmp.setNextY(null);
						tmp = tmp.getNextX();
						
					}
				}
			}
		}
		
		if(allowClipping) {
			
			current = head;
			tmp = current;
			
			InnerIterator iterator3 = new InnerIterator();

			while(iterator3.hasNext()) {

				eachSample = iterator3.next();
				
				for(int i = 0; i < eachSample.length; i++) {
					
					if(i == 0) {
						
						float theSample = tmp.elem();
						theSample = theSample/max;
						
						if(theSample >= 0) {
							
							tmp.setElem(1);
							tmp.setNextY(null);
							
						} else {
							
							tmp.setElem(-1);
							tmp.setNextY(null);
						}
						
						tmp = tmp.getNextX();
					}
				}
			}
		} // DONE
	}
	

	/** 
	 * Return a clone (deep copy) of the SoundList
	 */
	@Override
	public MusicList clone() {
		
		MusicList list2 = new MusicLinkedList(getSampleRate(), getNumChannels());
		InnerIterator iterator = new InnerIterator();
		
		for(int i = 0; i < numSamples; i++) {
			
			float[] arr = iterator.next();
			list2.addSample(arr);
			
		}
		
		return list2;
	}

	/**
	 * Goes through musicList and prints out elements within list 
	 *  */
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		Link row = head;
		
		
		/* goes through each row getting column by column */
		while (row != null) {
			
			/* Assigns first link in row to first link in column */
			Link col = row;
			int i = 0;
			
			while (col != null) {
				
				str.append(col.elem + "(" + i +") -> ");
				col = col.getNextY();
				
			}
			
			row = row.getNextX();
			str.append("\n");
			
		}

		return str.toString();
		//return "MusicLinkedList: SampleRate: " +sampleRate+ " NumChannels: " +numChannels;
		
	}
	
	
	/** 
	 * Single or Multi-Channel */
	@Override
	public Iterator<float[]> iterator() {
		
		InnerIterator iterator = new InnerIterator();
		return iterator;
		
	}

	/**
	 *  Single or MultiChannel */
	class InnerIterator implements Iterator<float[]>{

		private Link current;
		private Link previous;
		private Link tmp;

		public InnerIterator() {

			previous = null;
			current = head;
		}

		@Override
		public float[] next() {	

			float[] channels = new float[getNumChannels()];
			int i = 0;

			if(current != null) {
				tmp = current;

				while(i < getNumChannels()) {
					
					channels[i] = tmp.elem();
					i++;
					
					if(tmp.getNextY() != null) {
						tmp = tmp.getNextY();
					}
				}
				
				current = current.getNextX();
			}
			
			return channels;
		}

		public boolean hasNext() {
			
			if(getNumChannels() > 1) {
				
				if(current != null && current.getNextY() != null) {
					return true;
					
				} else {
					return false;
				}
				
			} else {
				
				if(current != null && current.getNextX() != null) {
					return true;
					
				} else {
					return false;
				}
				
			}
			
		}
	}
	
	
	/**
	 *  Used for Multi-Channel */
	@Override
	public Iterator<Float> iterator(int channel) {
		
		NewInnerIterator iterator = new NewInnerIterator(channel);
		return iterator;
		
	}

	/**
	 *  MultiChannel - called from TestMain 
	 *  */ 
	class NewInnerIterator implements Iterator<Float> {

		float info = 0.0f;
		Link newLink = new Link(info, null, null);
		
		
		public NewInnerIterator(int channels) {

			newLink = head;
			
			for (int i = 0; i < channels; i++) {
				
				newLink = newLink.getNextY();
			}
		}

		public Float next() {
			
			info = newLink.elem();
			newLink = newLink.getNextY();

			return info;
		}

		public boolean hasNext() {
			
			if(newLink != null && newLink.getNextY() != null) {
				return true;
				
			} else {
				return false;
			}
		}
		
	}


	/**
	 *  Used to test iterator (y links) */
	public void testIterator(Object sample1) {

		NewInnerIterator musIterator = new NewInnerIterator(0);
		Object testin = null;

		while(musIterator.hasNext()) {
			testin = musIterator.next();
		}
	}

	/**
	 *  Testing my iterator */
	public void testIterator(float[] sample1) {
		

		InnerIterator musIterator = new InnerIterator();
		float[] testin = new float[getNumSamples()];

		while(musIterator.hasNext()) {
			
			testin = musIterator.next();
			
			for (int y = 0; y < testin.length; y++) {
				System.out.print(" " +testin[y]+ ", ");
			}
			
			System.out.println();
		}
	}


	/** 
	 * USED FOR TESTING!!! TO RUN THIS PROJECT, RUN FROM TestMain.java
	 */
	public static void main(String[] args) {

		/*MusicLinkedList list = new MusicLinkedList(5, 5);
		MusicLinkedList list2 = new MusicLinkedList(5,5);
		MusicLinkedList listSingle = new MusicLinkedList(1, 1);
		
		float[] sample1 = {-0.1f,0.2f,-0.3f,4,0}; // 15
		float[] sample2 = {0,7,8,-9,0}; // 30
		float[] sample3 = {-0.1f,3.0f,0.5f,-7,0}; // 25
		float[] sample4 = {0,0.2f,0.44f,6,8}; // 20
		float[] sample5 = {10,12,14,16,18}; 
		float[] sample6 = {20,22,24,26,28}; 
		
		float x = 1;
		float y = 2;
		float a = 3;
		float b = 4;*/

		/*System.out.println("Single");
		listSingle.addSample(x);
		listSingle.addSample(y);
		listSingle.addSample(a);
		listSingle.addSample(b);

		listSingle.testIterator(x);
		System.out.println("Done Single");*/

		//System.out.println("Array");
		/*list.addSample(sample1);
		list.addSample(sample2);
		list2.addSample(sample3);
		list2.addSample(sample4);*/
		//list.addSample(sample3);
		//list.addSample(sample6);
		//list.addSample(sample4);
		//list.addSample(sample5);
		//list.testIterator(sample1);
		//list2.testIterator(sample3);

		//list.testIterator(sample2);
		//System.out.println("Done Array");
		//list.makeMono(true);

		//list.clip(.03f, .2f);
		//list.testIterator(sample1);
		//list.testIterator(sample2);
		//System.out.println("REVERSE1");
		
		//list.clone();
		
		//list.reverse();
		//System.out.println("REVERSE");
		//list.testIterator(sample1);

		/*list.testIterator(sample2);
		listSingle.addSample(x);
		listSingle.addSample(y);
		listSingle.addSample(a);
		listSingle.addSample(b);

		listSingle.testIterator(x);*/

		//System.out.println(list.toString());
		//System.out.println("Combine After: ");
		//System.out.println(list.toString());
	}
}

