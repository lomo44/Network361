package Network361;
enum RequestType{
	Echo,
};

enum RequestStatus{
	Done,
	Exit,
};

public abstract class Request{
	private RequestType requestType;
	public RequestType getRequestType() {
		return requestType;
	}
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}
	abstract public RequestStatus doRequest(Connection _connection);
}
