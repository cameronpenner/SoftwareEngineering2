CampaignView = React.createClass({

	getInitialState() {
		return({editing: false, canEdit: false, playerBackup: [], characterLookup: {}});
	},

	componentDidMount() {
		var self = this;
		Meteor.call("canEdit", this.props.campaign, function(err, data) { // move this to getMeteorData to change behavior of still being able to edit after GM swap
			if (data === true) {
				self.enableEditing();
			}
		});

		this.fillCharacterLookup();
	},

	render() {
		return(
			<div>
			{this.state.editing ? 
				<form>
					<input type="text" ref="name" className="form-control" defaultValue={this.props.campaign.name}/>
					<button type="button" className="btn btn-success" onClick={this.save}><span className="glyphicon glyphicon-ok"></span></button> 
					<div className ="list-group">
						{this.props.campaign.players.map((player, i) => {
							return <div key={i} className="list-group-item">{player} <button className="btn pull-right btn-xs btn-default" type="button" onClick={this.removePlayer.bind(this, player)}><span className="glyphicon glyphicon-remove"></span></button></div>;
						})}
					</div>

					<div className="input-group">
						<input type="text" className="form-control" placeholder="Add player..." ref="newplayer" />
						<span className="input-group-btn">
							<button className="btn btn-default" type="button" onClick={this.addPlayer}>Add</button>
						</span>

					</div>

					<h4>Game master:</h4>
					<select className="form-control" ref="gamemaster">
						<option>{this.props.campaign.game_master_name}</option>
						{this.props.campaign.players.map((player, i) => {
							if (player != this.props.campaign.game_master_name) {
								return <option key={i}>{player}</option>;
							} else {
								return null;
							}
						})}
					</select>
				</form>
			: 
				<div className="panel panel-default">
					<div className="panel-heading clearfix">
						{this.state.canEdit ? 
							<div>
								<button type="button" className="btn pull-right btn-danger btn-sm" onClick={this.props.onDelete.bind(null, this.props.campaign)}><span className="glyphicon glyphicon-remove"></span></button> 
								<button type="button" className="btn pull-right btn-default btn-sm" onClick={this.toggleEditing}><span className="glyphicon glyphicon-pencil"></span></button> 
							</div>
						: 
							""}
				        <h3 className="panel-title pull-left">{this.props.campaign.name}</h3>
					</div>
					<div className="panel-body">
						<h4>Game master: {this.props.campaign.game_master_name}</h4>
						<h4>Players</h4>
						<ul className="list-group">
							{this.props.campaign.players.map((player, i) => {
								return <li key={i} className="list-group-item">{player}</li>;
							})}
						</ul>
						<h4>Characters</h4>
						<ul className="list-group">
							{this.props.campaign.character_ids.map((characterId) => {
								var canEdit = false;
								this.props.characters.filter(function(curr) {
									if (curr._id === characterId) {
										canEdit = true;
									}
								});
								if (canEdit) {
									return (
										<li key={characterId} className="list-group-item"><a href={"/character/" + characterId}>{this.state.characterLookup[characterId] ? this.state.characterLookup[characterId].name : "loading..."}</a><button className="btn btn-xs btn-default pull-right" onClick={this.removeCharacter.bind(this, characterId)}><span className="glyphicon glyphicon-remove"></span></button></li>
									);
								} else {
									return (
										<li key={characterId} className="list-group-item">{this.state.characterLookup[characterId] ? this.state.characterLookup[characterId].name : "loading..."}</li>
									);
								}
								
							})}
						</ul>
						<h4>Add Character</h4>
						<div className="input-group">
							<select className="form-control" ref="addcharacter">
								{this.props.characters.map((character) => {
									if (this.props.campaign.character_ids.indexOf(character._id) == -1) {
										return <option key={character._id}>{character.name}</option>
									} else {
										return null;
									}
								})}
							</select>
							<span className="input-group-btn">
								<button className="btn btn-default" type="button" onClick={this.addCharacter}><span className="glyphicon glyphicon-plus"></span></button>
							</span>
						</div>
					</div>
				</div>}
			</div>
		);
	},

	enableEditing() {
		this.setState({canEdit: true});
	},

	toggleEditing() {
		if (!this.state.editing) { // editing is about to start
			this.setState({playerBackup: this.props.campaign.players});
		} else { // editing is done but not saved
			this.props.campaign.players = this.state.playerBackup;
		}
		this.setState({editing: !this.state.editing});
	},

	addPlayer() {
		var self = this;
		Meteor.call("playerExists", this.refs.newplayer.value, function(err, data) {
			if (data === true) {
				self.props.campaign.players.push(self.refs.newplayer.value);
				self.refs.newplayer.value = "";
				ReactDOM.findDOMNode(self.refs.newplayer).className += " has-success";
			} else {
				alert("Player does not exist.");
			}
			self.forceUpdate();
		});
	},

	addCharacter() {
		this.props.campaign.character_ids.push(this.props.characters[this.refs.addcharacter.selectedIndex]._id);
		jQuery.unique(this.props.campaign.character_ids);
		Meteor.call("upsertCampaign", this.props.campaign);
		this.fillCharacterLookup();
	},

	removeCharacter(character) {
		this.props.campaign.character_ids.splice(this.props.campaign.character_ids.indexOf(character._id), 1);
		Meteor.call("upsertCampaign", this.props.campaign);
		this.fillCharacterLookup();
	},

	removePlayer(player) {
		this.props.campaign.players.splice(this.props.campaign.players.indexOf(player), 1);
		this.forceUpdate();
	},

	save() {
		this.props.campaign.name = this.refs.name.value;
		this.props.campaign.game_master_name = this.refs.gamemaster.value;
		this.setState({editing: false});
		Meteor.call("upsertCampaign", this.props.campaign);
	},

	fillCharacterLookup() {
		var self = this;
		this.props.campaign.character_ids.map((characterId) => {
			Meteor.call("getCharacter", characterId, function(err, data) {
				characterLookup = self.state.characterLookup;
				characterLookup[characterId] = data;
				self.setState({characterLookup: characterLookup});
			});
		});
	}
});