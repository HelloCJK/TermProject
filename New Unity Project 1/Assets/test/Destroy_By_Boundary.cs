using UnityEngine;
using System.Collections;

public class Destroy_By_Boundary : MonoBehaviour {

    public GameObject shot;
   

    void Update()
    {
    }
    void OnTriggerExit(Collider other)
    {
        Destroy(other.gameObject);
    }
}
