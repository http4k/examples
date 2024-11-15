package com.example

import jakarta.servlet.Servlet
import jakarta.servlet.annotation.WebServlet
import org.http4k.servlet.jakarta.HttpHandlerServlet

@WebServlet("/")
class HelloWorldServlet : Servlet by HttpHandlerServlet(HelloWorld())
