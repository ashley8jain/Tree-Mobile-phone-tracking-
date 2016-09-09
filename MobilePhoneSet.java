public class MobilePhoneSet
{
	private Myset a=new Myset();	
	public void Insert(MobilePhone m)
	{	
		a.Insert(m);
	}
	public void Delete(MobilePhone m)
	{
		a.Delete(m);
	}
	public MobilePhone find(int n)
	{
		Node<Object> o=a.elementatTop();
		while (o!=null)
		{
			MobilePhone m=(MobilePhone)(o.getElement());
			if (m.number()==n)
				return m;
			o=o.getNext();
		}
		return null;
	}			
	public Node<Object> elementatTop()
	{			
		return a.elementatTop();
	}
}
