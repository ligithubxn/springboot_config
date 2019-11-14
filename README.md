# springboot+过滤器+拦截器
- 通过springboot实现filter与interceptor
- 拦截器可以用于swing，application，j2ee开发，过滤器只能用于web开发，是servlet规范规定的
- 过滤器
   - 自定义 filter 通过实现filter接口，实现三个dofilter，init，destroy方法，主要是dofilter实现过滤功能
   - HttpServletRequest继承自ServletRequest,可以获取到url
   - Calendar ca=Calendar.getInstance();  int hour=ca.get(Calendar.HOUR_OF_DAY);获取到现在的时间，项目通过控制时间来过滤请求，2-24点
   - OjectMapper把数据转化为String，response输出到前台
   - filterChain.doFilter(servletRequest,servletResponse) 操作dofilter，执行过滤
   - 最后装配到registrationBean之中去，返回registrationBean，并封装为bean
- 拦截器
   - addInterceptor：需要一个实现HandlerInterceptor接口的拦截器实例   addPathPatterns：用于设置拦截器的过滤路径规则
   - /**实现全路径拦截，自定义拦截器通过实现HandlerInterceptor，实现三个preHandle postHanle afterCompletion实现拦截
   - 通过ip比对实现ip拦截
   
