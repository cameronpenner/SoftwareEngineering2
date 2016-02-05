var newCampaignValues = function() {
    return {
        game_master: Meteor.user()._id,
        username: Meteor.user().emails[0].address,
        createdAt: new Date()
    };
}

Meteor.methods({
    insertCampaign: function(campaign) {
        if (!campaign || !Meteor.user()) return null;
        return Collections.Campaigns.insert(_.extend(campaign, newCharValues()));
    },

    addPlayer: function(player) {
        if (!player) return null;
    },

    upsertCampaign: function(campaign) {
        if (!campaign || !Meteor.user()) return null;
        return Collections.Campaigns.upsert({
            _id: campaign._id
        }, {
            $set: campaign
        }, {
            $setOnInsert: _.extend(campaign, newCampaignValues())
        });
    },
    updateCampaign: function(campaign) {
        return Collections.Campaigns.update(campaign);
    },
    removeCampaign: function(campaign) {
        return Collections.Campaigns.remove({_id: campaign._id});
    }
});