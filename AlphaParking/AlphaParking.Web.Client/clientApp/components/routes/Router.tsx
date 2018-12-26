import * as React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import Login from '../auth/Login';
import Registration from '../auth/Registration';
import VKAuthToServer from '../auth/VKAuthToServer';
import Hello from '../common/HelloComponent';
import Home from '../Home';

class RouterComponent extends React.Component {
    render() {
        return <Router>
            <>
                {/* TODO: Layout for every page */}
                <div>Шапка меню приложения для всех страниц</div>
                <hr />
                <Switch>
                    <Route exact path='/' component={Home} />
                    <Route path='/hello' component={Hello} />
                    <Route path='/registration' component={Registration} />
                    <Route path='/login' component={Login} />
                    <Route path='/vk/auth' component={VKAuthToServer} />
                </Switch>
            </>
        </Router>;
    }
}

export default RouterComponent;