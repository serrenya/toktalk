import React from "react";
import $ from "jquery";

class ChatArea extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            items: []
        };
        this.addItem = this.addItem.bind(this);
    }

    componentDidMount() {
        $.ajax({
            url: '/api/messages',
            method: 'GET',
            data: {channelId: this.props.cId}
        }).done(json => {
            var messages = [];
            for (let message of json) {
                messages.push(<ChatContent key={Date.now() + message.id} {...message}/>);
            }
            ;
            this.setState({items: messages});
        });
    }

    addItem(message) {
        this.setState((prevState) => {
            return {
                items: prevState.items.concat(<ChatContent key={Date.now() + message.id} {...message}/>)
            };
        });
        console.log(this.state.items);
    }

    render() {
        return (
            <div className="chat-area">
                {this.state.items}
            </div>
        );
    }
}

class ChatContent extends React.Component {
    render() {
        return (
            <div className="chat-content">
                <span className="time">{this.props.regdate}</span>
                <span className="content">{this.props.text}</span>
            </div>
        );
    }
}

export default ChatArea;