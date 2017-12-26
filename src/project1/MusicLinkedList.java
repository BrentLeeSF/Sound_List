package project1;


import java.util.Iterator;


public class MusicLinkedList implements MusicList {

	InnerIterator iterator;
	private float sampleRate;
	private int sampleSize;
	private int numChannels;
	
	private Link head, tail, current, prevCol, colHead, newColHead, firstNode, lastNode = null;
	
	private int numSamples = 0;
	private float duration;
	
	LinkedList list = new LinkedList();
	
	

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
	
	//TODO 15pts
	@Override
	public void changeSampleRate(float newRate) {
		//sampleRate = newRate*getSampleRate();
	}
	
	// TODO 10pts - Adds an echo effect to the soundList 
	@Override
	public void addEcho(float delay, float percent) {
		
	}
	
	
	
	// TODO 5pts
		/* Combine(SoundList clipToCombine, boolean allowClipping) 
		 * Add the waveform of clipToCombine to this clip. 
		 * If allowClipping is true, clip all samples in the range -1, 1. 
		 * If allowClipping is false, rescale resuLting waveform to be in the range -1, 1 
		 */
		/* If allowClipping is TRUE - Iterate through both the original musicList and the clipToCombine.
		 * Starting at the head of the musicList and the head of the clipToCombine
		 */
		@Override
		public void combine(MusicLinkedList clipToCombine, boolean allowClipping) {
			
			// TODO
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

			//TODO
			//TODO
			//TODO
			// iterate through entire structure, save biggest sample in entire thing
			// iterate through again, and divide by max
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
						//System.out.println("INSIDE: "+eachSample[i]);
					}
					tmp = tmp.getNextX();
				}
			}

		} // end method
		
		
	//TODO 5pts
	/* Clips the SoundList by throwing away all samples before the startTime (in seconds), 
	 * and after the duration (in seconds). So, if the SoundList was 6 seconds long, and we called clip(4,2), 
	 * the new SoundList would be 2 seconds long (and would consist of samples from second 4 to second 6 
	 * of the original SoundList)
	 */
	@Override
	public void clip(float startTime, float duration) {

			// start after node to start, if 14, start at 15. if stop at 85, really stop at 84.
			// startingSample(Index) = startTime* sampleRate
			// endTime = (startTime + duration)(sampleRate)

			
			InnerIterator iterator = new InnerIterator();
			float[] eachSample = new float[getNumChannels()];
			
			Link current = head;
			Link beginPointer = head;
			float begin = startTime*sampleRate;
			float end = (startTime+duration)*sampleRate;
			float start = 0.0f;
			int a = 0;
			int index = 0;
			
		while(iterator.hasNext()) {
			eachSample = iterator.next();
			for(int i = 0; i < eachSample.length; i++) {
				start +=  1.0 / (double) sampleRate;
				if(start >= startTime&& i > 0) {
					i++;
					//System.out.println("MONO First**: " +start);
				}
			}
			//System.out.println("MONO First: " +start);	
		}
	}

	// TODO 5pts
	/* Splice (join) clipToSplice into this clip, starting at startSpliceTime 
	 */
	@Override
	public void spliceIn(float startSpliceTime, MusicList clipToSplice) {
		/* Iterate through list until specific time. Once at time, save link with spliceTime and
		 * next link with one after spliceTime. NewMusicList links to link with SpliceTime and
		 * last link of NewMusicList links to originalMusicList's next link (link after SpliceTime)
		 */
	}


	/* Reverses the musicLinkedList
	 */
	@Override
	public void reverse() {

		Link previous = null;
		Link current = head;
		Link newLink = null;

		while(current != null) {
			if(getNumChannels() > 1) {
				newLink = current.getNextY();
				current.setNextY(previous);
			}
			else {
				newLink = current.getNextX();
				current.setNextX(previous);
			}
			previous = current;
			current = newLink;
		}
	}


	/* Changes sample rate
	 * @parameter percentageChange - is multiplied to the sampleRate to change sampleRate
	 */
	@Override
	public void changeSpeed(float percentChange) {
		sampleRate *= percentChange;
	}


	/* Adds a sample to the end of the SoundList. If the SoundList is not single-channel, 
	 * this method should throw and IllegalArgument exception 
	 * @parameter sample - sample rate per second
	 */
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

	/* Adds a sample to the end of the SoundList. If the length of the sample array is not 
	 * the same as the number of channels in the SampleList, throw an IllegalArgument exception
	 * @parameter sample[] - 
	 */
	@Override
	public void addSample(float[] sample) {

		numSamples++;

		if(numChannels > getNumChannels()) {
			throw new IllegalArgumentException();
		}
		if(head != null) {

			Link tmp = tail;
			int j = 0;

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
		}

		//build the first linked list
		else {
			for(int i = 0; i < sample.length; i++) {
				if(i > 0) {
					Link newNodes = new Link(sample[i], null, null);
					current.setNextY(newNodes);
					current = newNodes;
				}
				else {
					Link newNode = new Link(sample[i], null, null);
					head = tail = newNode;
					current = newNode;
					prevCol = newNode;
				}
			}
		}
		
	}


	/* Combine all channels into one. If allowClipping is true, 
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
		//allowClipping = false;
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
						}
						else {
							tmp.setElem(-1);
							tmp.setNextY(null);
						}
						tmp = tmp.getNextX();
					}
				}
			}
		} // DONE
	}


	

	/* Determines if the head of the linked list is null or not
	 */
	public boolean isEmpty() {
		return head == null;
	}

	/* Determines if there is a previous link
	 */
	public boolean hasPrevious() {
		return current != head && current != null;
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
	 *  Goes through musicList and prints out elements within list
	 */
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		Link row = head;
		
		//going next
		while (row != null) {
			//going up
			Link col = row;
			int i=0;
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

	public Iterator<float[]> listIterator() 
	{
		return new InnerIterator(0);
	}

	public Iterator<Float> listIterator(int index) 
	{
		return new NewInnerIterator(index);
	}

	class InnerIterator implements Iterator<float[]>{

		private Link current;
		private Link previous;
		private Link tmp;

		public InnerIterator() {
			previous = null;
			current = head;
		}

		public InnerIterator(int index) {
			previous = null;
			current = head;
			for (int i = 0; i < index; i++) {
				previous = current;
				current = current.getNextY();
			}
		}

		@Override
		public float[] next() {	

			float[] channels = new float[getNumChannels()]; //[getNumSamples()]; //
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

		public boolean hasNextY() {
			return current.getNextY() != null;
		}

		public boolean hasNextX() {
			return current.getNextX() != null;
		}

		public boolean hasNext()
		{
			if(getNumChannels() > 1) {
				if(current != null && current.getNextY() != null) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				if(current != null && current.getNextX() != null) {
					return true;
				}
				else {
					return false;
				}
			}
			
		}
	}


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
			}
			else {
				return false;
			}
		}

		public boolean hasNextY() {
			return newLink != null && newLink.getNextY() != null;
		}
	}



	@Override
	public Iterator<Float> iterator(int channel) {
		NewInnerIterator iterator = new NewInnerIterator(channel);
		return iterator;
	}

	@Override
	public Iterator<float[]> iterator() {
		InnerIterator iterator = new InnerIterator();
		return iterator;
	}

	public Link getFirstNode() {
		return firstNode;
	}

	public void setFirstNode(Link firstNode) {
		this.firstNode = firstNode;
	}

	public Link getLastNode() {
		return lastNode;
	}

	public void setLastNode(Link lastNode) {
		this.lastNode = lastNode;
	}


	public void testIterator(Object sample1) {

		NewInnerIterator musIterator = new NewInnerIterator(0);
		Object testin = null;

		while(musIterator.hasNext()) {
			testin = musIterator.next();
		}
	}

	public void testIterator(float[] sample1) {

		InnerIterator musIterator = new InnerIterator();
		int i = 0;
		float[] testin = new float[getNumSamples()];

		while(musIterator.hasNext()) {
			testin = musIterator.next();
			for (int y = 0; y < testin.length; y++) {
				System.out.print(" " +testin[y]+ ", ");
			}
			System.out.println();
			// current = current.getNextY();
			i++;
		}
	}


	/* 
	 * USED FOR TESTING!!! TO RUN THIS PROJECT, RUN FROM TestMain.java
	 */
	public static void main(String[] args) {

		MusicLinkedList list = new MusicLinkedList(5, 5);
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
		float b = 4;

		/*System.out.println("Single");
		listSingle.addSample(x);
		listSingle.addSample(y);
		listSingle.addSample(a);
		listSingle.addSample(b);

		listSingle.testIterator(x);
		System.out.println("Done Single");*/

		//System.out.println("Array");
		list.addSample(sample1);
		list.addSample(sample2);
		list2.addSample(sample3);
		list2.addSample(sample4);
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
		System.out.println("Combine Before: ");
		System.out.println("List1: \n"+list.toString());
		System.out.println("List2: \n"+list2.toString());
		System.out.println("Combining");
		list.combine(list2, false);
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
		System.out.println("Combine After: ");
		System.out.println(list.toString());
	}
}