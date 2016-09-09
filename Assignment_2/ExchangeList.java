public class ExchangeList
{	
	private Node<Exchange> top;
	public ExchangeList(){}
	public boolean isEmpty()
	{
		return (top==null);
	}
	public Node<Exchange> getTop()
	{
		return top;
	}
	public void Insert(Exchange e)
	{		
		top=new Node<Exchange>(e,top);		
	}
	public void Delete(Exchange e) 
	{
		Node<Exchange> n=top;
		boolean flag=false;
		if (n.getElement().getIdfr()==e.getIdfr())
		{
			top=n.getNext();
			flag=true;
		}
		else 
		{
			while (n.getNext()!=null)
			{
				Node<Exchange> m=n.getNext();
				if (m.getElement().getIdfr()==e.getIdfr())
				{
					n.setNext(m.getNext());
					flag=true;
					break;
				}
				n=m;
			}	
	 	}
		if (flag==false)
			throw new RuntimeException("Not Found");
	}
}
