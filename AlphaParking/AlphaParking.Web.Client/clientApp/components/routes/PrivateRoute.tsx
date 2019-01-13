import * as React from 'react';
import { Redirect, Route, RouteComponentProps } from 'react-router-dom';

interface IProps {
    isAuth: boolean;
    component: React.ComponentType<RouteComponentProps<any>> | React.ComponentType<any>;
    path: string;
}

const PrivateRoute: React.SFC<IProps> = ({ component, isAuth, path }) => {
    return isAuth ?
        <Route path={path} component={component} /> :
        <Redirect to='/forbidden' />;
};

export default PrivateRoute;