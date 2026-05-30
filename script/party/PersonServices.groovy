import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityValue

ExecutionContext ec = context.ec

// 1. Validate required parameters
if (!partyId) { ec.message.addError("partyId is required"); return }
if (!firstName) { ec.message.addError("firstName is required"); return }
if (!lastName) { ec.message.addError("lastName is required"); return }

// 2. Verify that a Party record exists for the given partyId
EntityValue party = ec.entity.find("party.Party").condition("partyId", partyId).one()
if (party == null) {
    ec.message.addError("Party with ID [${partyId}] does not exist.")
    return
}

// 3. Ensure the Person is created only if the Party exists
// Create the Person value and set fields from context, ignoring non-entity fields
EntityValue newPerson = ec.entity.makeValue("party.Person")
newPerson.setFields(context, true, null, false)

// 4. Setting the required/validated fields
newPerson.partyId = partyId
newPerson.firstName = firstName
newPerson.lastName = lastName
if (dateOfBirth) newPerson.dateOfBirth = dateOfBirth

// Create or update the Person record
newPerson.createOrUpdate()

// 5. Return the response string
context.response = "Person ${firstName} ${lastName} created successfully!"
