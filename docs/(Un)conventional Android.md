(Un)conventional Android


# Introduction

As we develop our applications, we face two synergistic forces: Mishandled complexity and entropy. Often, these two ideas create something we call "Technical Debt." This happens with nearly all application development, save perhaps very simple applications. Sometimes we consciously and deliberately create debt. Other times debt increases in a stealthy manner, only to show its ugliness at the most inopportune time...release day, or worse, after release day.

Complexity is inevitable. Sometimes we allow the complexity in, and sometimes it creeps in unnoticed. But complexity comes with the territory. If we mismanage complexity, we create entropy in our system.

Entropy is a gradual decline into disorder. Evidence of entropy can be identified in applications while looking at the source code and while running the application. 

As a user of an application, when might you see entropy?
	* an application loses your preferences
	* when a scroll is choppy
	* when you get ANRs, or application not responding, messages
	* when an application crashes. 

As a developer, you'll see entropy too. What are some signs of entropy in your applications?
	* when a class or method is so long you have difficulty absorbing its purpose and function
	* when you can't remember or discover if the application has a specific algorithm

Entropy has an unmatched ability to coerce us into adding additional complexity into our application. I know you've encountered this. How have you (badly) dealt with entropy? There is no judgment here. We've all done it. This is a safe environment. :)
	* you add yet another "else if" block or "case" block to a method 
	* you write the same data or API access code for a third, fourth, fifth time
	* or worse, you realize you have code that is inefficient and complex. (That's not bad!) You write a more efficient and elegant implementation. (Great job!) You leave the old way in because its so entrenched in your code it would take more effort than you're willing to invest to re-implement.

And as complexity increases, entropy increases, and these two forces fan each others flames if not put in check.

