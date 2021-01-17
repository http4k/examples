import React from 'react';
import Home from "./pages/Home";
import {BrowserRouter, Route, Switch} from "react-router-dom";

function Http4kReact() {
    return <BrowserRouter>
        <nav>
        </nav>
        <div>
            <Switch>
                <Route path="/" component={() => Home()} exact/>
            </Switch>
        </div>
    </BrowserRouter>;
}

export default Http4kReact;
