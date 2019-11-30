import React, {Component} from "react";
import axios from 'axios';


class Test extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: {
                id: "",
                name: "",
                password: "",
                active: "",
                roles: ""
            }
        }
    }

    async componentDidMount() {
        const response =await fetch ('http://localhost:8080/api/users')
        const body = await response.json();
        console.log(body.error);
        if (body.error == "Unauthorized"){
            window.location.href = "http://localhost:8080/login";
        }
        //     this.getUsers();
            }
    //
    // getUsers = async () => {
    //     axios.get('/api/users')
    //         .then(res => {
    //             this.setState({user: res.data});
    //             console.log(res);
    //             console.log("res")
    //         }).catch((e) => console.log(e));
    //     console.log("method called");
    // };

    render() {
        let users = this.state.user;

        return (
            <div>{users[0]}</div>

        )
    }

}

export default Test;