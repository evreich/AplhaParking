import * as React from 'react';
import { Redirect, Route, RouteComponentProps } from 'react-router-dom';

interface IProps {
    isAuth: boolean;
    component: React.ComponentType<RouteComponentProps<any>> | React.ComponentType<any>;
    path: string;
    rest?: any;
}

const PrivateRoute: React.SFC<IProps> = ({ component, isAuth, path, ...rest }) => {
    return isAuth ?
        <Route path={path} component={component} {...rest} /> :
        <Redirect to='/forbidden' />;
};

export default PrivateRoute;