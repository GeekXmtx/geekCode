package geekCode03.testGateWay.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        // 做结果拦截
        response.headers().set("flag", "xm");
    }
}
