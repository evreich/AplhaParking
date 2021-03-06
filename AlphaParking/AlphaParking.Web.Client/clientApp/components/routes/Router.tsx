import * as React from 'react';
import { Route, Switch } from 'react-router-dom';

import Login from '../auth/Login';
import Registration from '../auth/Registration';
import VKAuthServerWaiter from '../auth/VKAuthServerWaiter';
import UserCars from '../cars/UserCars';
import ForbiddenErrorPage from '../common/ForbiddenErrorPage';
import Home from '../Home';
import AllParkingPlaces from '../parkPlaces/AllParkingPlaces';
import NotAuthOnlyRoute from './NotAuthOnlyRoute';
import PrivateRoute from './PrivateRoute';

interface IProps {
    isAuth: boolean;
    userRoles: string[];
}

const RouterComponent: React.SFC<IProps> = (props) => {
    const { isAuth, userRoles } = props;

    return <Switch>
            <NotAuthOnlyRoute exact path='/' component={Home} isAuth={isAuth}/>
            <NotAuthOnlyRoute path='/registration' component={Registration} isAuth={isAuth}/>
            <NotAuthOnlyRoute path='/login' component={Login} isAuth={isAuth}/>
            <NotAuthOnlyRoute path='/vk/auth' component={VKAuthServerWaiter} isAuth={isAuth}/>
            <Route path='/forbidden' component={ForbiddenErrorPage}/>
            <PrivateRoute path='/cars' component={UserCars} isAuth={isAuth} userRoles={userRoles}/>
            <PrivateRoute path='/parkPlaces' component={AllParkingPlaces} isAuth={isAuth} userRoles={userRoles} />
        </Switch>;
};

export default RouterComponent;