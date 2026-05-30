import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityValue

ExecutionContext ec = context.ec

// 1. Validate required parameters
if (!partyId) { ec.message.addError("partyId is required"); return }

// 2. Find the Person record
EntityValue person = ec.entity.find("party.Person").condition("partyId", partyId).one()
if (person == null) {
    ec.message.addError("Person with ID [${partyId}] does not exist.")
    return
}

// 3. Update fields from context
person.setFields(context, true, null, false)

// 4. Update the record
person.update()

// 5. Return the response string
context.response = "Person with ID [${partyId}] updated successfully!"
