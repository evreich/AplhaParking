import * as React from 'react';
import { Redirect, Route, RouteComponentProps } from 'react-router-dom';

interface IProps {
    isAuth: boolean;
    component: any;
    path: string;
    exact?: boolean;
}

const NotAuthOnlyRoute: React.SFC<IProps> = ({ component, isAuth, path, exact, ...rest }) => {
    return !isAuth ?
        <Route path={path} component={component} exact={exact} {...rest} /> :
        <Redirect to='/cars' />;
};

export default NotAuthOnlyRoute;