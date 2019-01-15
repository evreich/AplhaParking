import * as React from 'react';
import { Redirect, Route, RouteComponentProps } from 'react-router-dom';

import * as AppConsts from '../../constants/common';

interface IProps {
    isAuth: boolean;
    component: React.ComponentType<RouteComponentProps<any>> | React.ComponentType<any>;
    path: string;
    userRoles: string[];
}

const PrivateRoute: React.SFC<IProps> = ({ component, isAuth, path, userRoles }) => {
    return isAuth && userRoles.find((elem) => elem === AppConsts.EMPLOYEE_ROLE) ?
        <Route path={path} component={component} /> :
        <Redirect to='/forbidden' />;
};

export default PrivateRoute;