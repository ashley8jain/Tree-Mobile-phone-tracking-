public class Myset {		
		
		private Node<Object> top;
		Myset()
		{
			top=null;
		}
		public Node<Object> elementatTop()
		{
			return top;
		}
		public boolean IsEmpty()
		{ 
	      return (top==null);
		}
		
		public boolean IsMember(Object o)
		{ Node<Object> p=top;
		  while(p!=null)
		  {
			  if(p.getElement().equals(o))
				  return true;
			  p=p.getNext();
		  }
		  return false;
		}
		public void Insert(Object o)
		{  top=new Node<Object>(o,top);				
		}
		public void Delete(Object o)
		{ Node<Object> p=top;
		  boolean flag=false;
		  if(p.getElement().equals(o))
		  {
			  top=p.getNext();
			  flag=true;
		  }
		  else
		  {
			  while(p.getNext()!=null)
			  {
				  Node<Object> n=p.getNext();
				  if(n.getElement().equals(o))
				  {
					  p.setNext(n.getNext());
					  flag=true;
					  break;
				  }
				p=n;  
			  }
		  }
		  if(flag==false)
			  throw new RuntimeException("Not Found");
		}
		public Myset Union(Myset a)
		{ Myset final1=new Myset();
		Node<Object> p=top;
		while(p!=null)
		{
			final1.Insert(p);
			p=p.getNext();
		}
		Node<Object> tmp=a.elementatTop();
		
		boolean flag;
		while(tmp!=null)
		{	flag=false;
		    Node<Object> m=top;
			while(m!=null)
			{  
				if(m.getElement().equals(tmp))
					{flag=true;
					 break;
					}
				m=m.getNext();
			}
		    if(!flag)
		    	final1.Insert(tmp);
			tmp=tmp.getNext();
		}
		return final1;
		}
		public Myset Intersection(Myset a)
		{  
			Myset final2=new Myset();
			Node<Object> p=top;
			while(p!=null)
			{
				Node<Object> tmp=a.elementatTop();
				while(tmp!=null)
				{
					if(p.getElement().equals(tmp.getElement()))
						final2.Insert(p);
					tmp=tmp.getNext();
				}
				p=p.getNext();
			}
			return final2;
		}
	
}
