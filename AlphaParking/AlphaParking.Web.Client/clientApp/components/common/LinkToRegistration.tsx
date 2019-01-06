import * as React from 'react';
import { RouteComponentProps, withRouter } from 'react-router-dom';

interface IProps extends RouteComponentProps {
    text: string;
}

const LinkToRegistration: React.SFC<IProps> = (props) => {
    const { history: { push }, text } = props;
    return <a onClick={() => push('/registration')}>{text}</a>;
} ;

export default withRouter(LinkToRegistration);