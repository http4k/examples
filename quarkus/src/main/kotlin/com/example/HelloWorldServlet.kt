package com.example

import org.http4k.servlet.HttpHandlerServlet
import javax.servlet.Servlet
import javax.servlet.annotation.WebServlet

@WebServlet("/")
class HelloWorldServlet : Servlet by HttpHandlerServlet(HelloWorld())
