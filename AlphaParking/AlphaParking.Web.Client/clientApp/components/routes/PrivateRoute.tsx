import * as React from 'react';
import { Redirect, Route, RouteComponentProps } from 'react-router-dom';

interface IProps {
    isAuth: boolean;
    component: React.ComponentType<RouteComponentProps<any>> | React.ComponentType<any>;
    path: string;
    rest?: any;
}

const PrivateRoute: React.SFC<IProps> = ({ component, isAuth, path, ...rest }) => (
    <Route path={path} render={(props) => (
        isAuth ? {component} : <Redirect to='/forbidden' />
    )} {...rest} />);

export default PrivateRoute;