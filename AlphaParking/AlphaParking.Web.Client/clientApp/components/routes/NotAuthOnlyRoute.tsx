import * as React from 'react';
import { Redirect, Route, RouteComponentProps } from 'react-router-dom';

interface IProps {
    isAuth: boolean;
    component: React.ComponentType<RouteComponentProps<any>> | React.ComponentType<any>;
    path: string;
    exact?: boolean;
}

const NotAuthOnlyRoute: React.SFC<IProps> = ({ component, isAuth, path, exact, ...rest }) => (
    <Route path={path} render={(props) => (
        !isAuth ? {component} : <Redirect to='/cars' />
    )} {...rest} />);

export default NotAuthOnlyRoute;